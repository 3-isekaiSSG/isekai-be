package com.isekai.ssgserver.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.review.dto.ReviewProductResDto;
import com.isekai.ssgserver.review.dto.ReviewReqDto;
import com.isekai.ssgserver.review.service.ReviewService;
import com.isekai.ssgserver.util.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Tag(name = "Review", description = "리뷰 API document")
public class ReviewController {

	private final ReviewService reviewService;
	private final JwtProvider jwtProvider;

	// 리뷰 생성
	@PostMapping("/{order_product_id}")
	@Operation(summary = "리뷰 생성", description = "회원이 작성한 리뷰를 저장합니다.")
	public ResponseEntity<Void> createReview(
		@RequestHeader("Authorization") String token,
		@RequestBody ReviewReqDto reviewReqDto,
		@PathVariable Long order_product_id) {

		String uuid = jwtProvider.getUuid(token);
		reviewService.createReview(uuid, reviewReqDto, order_product_id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{product_id}/list")
	@Operation(summary = "단일 상품 전체 리뷰 조회", description = "단일 상품에 대한 전체 리뷰를 조회합니다.")
	public ResponseEntity<?> getProductReviewList(
		@PathVariable Long product_id,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize
	) {
		Page<ReviewProductResDto> reviewList = reviewService.getProductReviewList(product_id, page, pageSize);
		return ResponseEntity.ok(reviewList);
	}

	@GetMapping("/{review_id}")
	@Operation(summary = "특정 리뷰 조회", description = "특정 리뷰를 한개를 조회합니다.")
	public ResponseEntity<ReviewProductResDto> getReviewDetails(
		@PathVariable Long review_id
	) {
		ReviewProductResDto reviewDetails = reviewService.getReviewDetails(review_id);
		return ResponseEntity.ok(reviewDetails);
	}

	// // 리뷰 갯수
	// @GetMapping("/number")
	// public ResponseEntity<?> getReviewCountList() {
	// 	return ResponseEntity.ok().build();
	// }
	//
	// // 리뷰 수정
	// @PutMapping("{}")
	// public ResponseEntity<?> updateReview(
	// 	@RequestHeader("Authorization") String token
	//
	// ) {
	// 	String uuid = jwtProvider.getUuid(token);
	//
	// 	return ResponseEntity.ok().build();
	// }
	//
	// // 리뷰 삭제
	// @DeleteMapping("{reviewId}")
	// public ResponseEntity<?> deleteReview(
	// 	@PathVariable Long reviewId
	// ) {
	// 	return ResponseEntity.ok().build();
	// }

}
