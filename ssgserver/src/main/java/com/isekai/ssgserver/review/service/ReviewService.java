package com.isekai.ssgserver.review.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.entity.Member;
import com.isekai.ssgserver.member.repository.MemberRepository;
import com.isekai.ssgserver.order.entity.OrderProduct;
import com.isekai.ssgserver.order.repository.OrderProductRepository;
import com.isekai.ssgserver.review.dto.ReviewCountDto;
import com.isekai.ssgserver.review.dto.ReviewCountResDto;
import com.isekai.ssgserver.review.dto.ReviewProductResDto;
import com.isekai.ssgserver.review.dto.ReviewReqDto;
import com.isekai.ssgserver.review.entity.Review;
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

	}

	@Transactional
	public Page<ReviewProductResDto> getProductReviewList(Long productId, int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);

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
		Review review = reviewRepository.findByReviewId(reviewId);

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
}
