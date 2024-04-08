package com.isekai.ssgserver.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@PostMapping("/{orderProduct}")
	@Operation(summary = "리뷰 생성", description = "회원이 작성한 리뷰를 저장합니다.")
	public ResponseEntity<Void> createReview(
		@RequestHeader("Authorization") String token,
		@RequestBody ReviewReqDto reviewReqDto,
		@PathVariable Long orderProduct) {

		String uuid = jwtProvider.getUuid(token);
		reviewService.createReview(uuid, reviewReqDto, orderProduct);
		return ResponseEntity.ok().build();
	}

	// // 특정 리뷰 조회
	// @GetMapping("/{}")
	// public ResponseEntity<?> getReviewDetails() {
	// 	return ResponseEntity.ok().build();
	// }
	//
	// // 상품 리뷰 여러개 조회
	// @GetMapping("/list")
	// public ResponseEntity<?> getReviewList() {
	// 	return ResponseEntity.ok().build();
	// }
	//
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
