package com.isekai.ssgserver.review.service;

import java.time.LocalDateTime;

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
			return new ReviewProductResDto(reviewId, score, reviewContent, accountId, productIdMod, reviewImage,
				createdAt);
		});
	}

}
