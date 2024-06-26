package com.isekai.ssgserver.review.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.entity.Member;
import com.isekai.ssgserver.member.repository.MemberRepository;
import com.isekai.ssgserver.order.entity.OrderProduct;
import com.isekai.ssgserver.order.repository.OrderProductRepository;
import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.product.entity.ReviewScore;
import com.isekai.ssgserver.product.repository.ProductRepository;
import com.isekai.ssgserver.product.repository.ReviewScoreRepository;
import com.isekai.ssgserver.review.dto.ReviewCountDto;
import com.isekai.ssgserver.review.dto.ReviewCountResDto;
import com.isekai.ssgserver.review.dto.ReviewPhotoResDto;
import com.isekai.ssgserver.review.dto.ReviewProductResDto;
import com.isekai.ssgserver.review.dto.ReviewReqDto;
import com.isekai.ssgserver.review.entity.Review;
import com.isekai.ssgserver.review.enums.ReviewType;
import com.isekai.ssgserver.review.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final MemberRepository memberRepository;
	private final OrderProductRepository orderProductRepository;
	private final ReviewScoreRepository reviewScoreRepository;
	private final ProductRepository productRepository;

	@Transactional
	public void createReview(String uuid, ReviewReqDto reviewReqDto, Long orderProductId) {
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		Review review = Review.builder()
			.orderProduct(orderProduct)
			.uuid(uuid)
			.accountId(member.getAccountId())
			.score(reviewReqDto.getScore())
			.reviewContent(reviewReqDto.getReviewContent())
			.reviewImage(reviewReqDto.getReviewImage())
			.productId(reviewReqDto.getProductId())
			.build();

		reviewRepository.save(review);

		updateReviewScore(reviewReqDto.getScore(), reviewReqDto.getProductId());
	}

	@Transactional
	private void updateReviewScore(int score, Long productId) {
		Product product = productRepository.findByProductId(productId);
		String productCode = product.getCode();
		ReviewScore reviewScore = reviewScoreRepository.findByProductCode(productCode)
			.orElseGet(() -> createNewReviewScore(productCode));

		long reviewCount = reviewScore.getReviewCount() + 1;
		long totalScore = reviewScore.getTotalScore() + score;
		double avgScore = (double)totalScore / reviewCount;

		reviewScore.setReviewCount(reviewCount);
		reviewScore.setTotalScore(totalScore);
		reviewScore.setAvgScore(avgScore);

		reviewScoreRepository.save(reviewScore);
	}

	@Transactional
	private ReviewScore createNewReviewScore(String productCode) {
		return ReviewScore.builder()
			.avgScore(0.0)
			.reviewCount(0L)
			.totalScore(0L)
			.productCode(productCode)
			.build();
	}

	@Transactional
	public Page<ReviewProductResDto> getProductReviewListSorted(Long productId, int page, int pageSize,
		ReviewType sortType) {
		Pageable pageable;
		if (sortType == ReviewType.LATEST) {
			pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
		} else if (sortType == ReviewType.HIGHEST_RATING) {
			pageable = PageRequest.of(page, pageSize, Sort.by("score").descending());
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("score").ascending());
		}

		Page<Object[]> reviewsPage = reviewRepository.findByProductId(productId, pageable);

		return reviewsPage.map(result -> {
			LocalDateTime createdAt = (LocalDateTime)result[0];
			String accountId = (String)result[1];
			Long productIdMod = (Long)result[2];
			String reviewContent = (String)result[3];
			String reviewImage = (String)result[4];
			byte score = (byte)result[5];
			Long reviewId = (Long)result[6];

			String maskedAccountId = maskAccountId(accountId);

			return new ReviewProductResDto(reviewId, score, reviewContent, maskedAccountId, productIdMod, reviewImage,
				createdAt);
		});
	}

	@Transactional
	public Page<ReviewPhotoResDto> getProductPhotoReviewList(Long productId, int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Object[]> reviewsPhoto = reviewRepository.findByProductIdAndImage(productId, pageable);

		return reviewsPhoto.map(result -> {
			Long reviewId = (Long)result[0];
			String reviewImage = (String)result[1];
			Long productIdMod = (Long)result[2];
			LocalDateTime createdAt = (LocalDateTime)result[3];

			return new ReviewPhotoResDto(reviewId, reviewImage, productIdMod, createdAt);
		});
	}

	private String maskAccountId(String accountId) {
		if (accountId.length() <= 3) {
			return accountId;
		} else {
			String prefix = accountId.substring(0, 3);
			String maskedAccountId = prefix + "*".repeat(accountId.length() - 3);
			return maskedAccountId;
		}
	}

	@Transactional
	public ReviewProductResDto getReviewDetails(Long reviewId) {
		Review review = reviewRepository.findByReviewId(reviewId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		String maskedAccountId = maskAccountId(review.getAccountId());

		return ReviewProductResDto.builder()
			.reviewId(review.getReviewId())
			.score(review.getScore())
			.reviewContent(review.getReviewContent())
			.accountId(maskedAccountId)
			.productId(review.getProductId())
			.reviewImage(review.getReviewImage())
			.createdAt(review.getCreatedAt())
			.build();
	}

	@Transactional
	public ReviewCountResDto getReviewCountList(Long productId) {
		Long totalCount = reviewRepository.countByProductId(productId);
		Long imageCount = reviewRepository.countByProductIdAndImage(productId);

		return ReviewCountResDto.builder()
			.reviewCountList(new ArrayList<ReviewCountDto>() {{
				add(new ReviewCountDto("totalCount", totalCount));
				add(new ReviewCountDto("imageCount", imageCount));
			}})
			.build();
	}

	@Transactional
	public void updateReviewContents(String uuid, Long reviewId, ReviewReqDto reviewReqDto) {
		Review review = reviewRepository.findByReviewIdAndUuid(reviewId, uuid)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		ModelMapper modelMapper = new ModelMapper();
		Review updatedReview = modelMapper.map(review, Review.class);

		String maskedAccountId = maskAccountId(review.getAccountId());
		updatedReview.setAccountId(maskedAccountId);
		updatedReview.setReviewContent(reviewReqDto.getReviewContent());
		updatedReview.setReviewImage(reviewReqDto.getReviewImage());
		updatedReview.setScore(reviewReqDto.getScore());
		reviewRepository.save(updatedReview);
	}

	@Transactional
	public void deleteReview(String uuid, Long reviewId) {
		Review reviewOptional = reviewRepository.findByReviewIdAndUuid(reviewId, uuid)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
		reviewRepository.deleteByReviewId(reviewId);
	}
}
