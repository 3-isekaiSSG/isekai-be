package com.isekai.ssgserver.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.review.dto.ReviewCountResDto;
import com.isekai.ssgserver.review.dto.ReviewPhotoResDto;
import com.isekai.ssgserver.review.dto.ReviewProductResDto;
import com.isekai.ssgserver.review.dto.ReviewReqDto;
import com.isekai.ssgserver.review.enums.ReviewType;
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

	@PostMapping("/{orderProductId}")
	@Operation(summary = "리뷰 생성", description = "회원이 작성한 리뷰를 저장합니다.")
	public ResponseEntity<Void> createReview(
		@RequestHeader("Authorization") String token,
		@RequestBody ReviewReqDto reviewReqDto,
		@PathVariable Long order_product_id) {

		String uuid = jwtProvider.getUuid(token);
		reviewService.createReview(uuid, reviewReqDto, order_product_id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{productId}/list")
	@Operation(summary = "단일 상품 전체 리뷰 조회", description = "단일 상품에 대한 전체 리뷰를 조회합니다.")
	public ResponseEntity<Page<ReviewProductResDto>> getProductReviewList(
		@PathVariable Long product_id,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize,
		@RequestParam(defaultValue = "LATEST") ReviewType sortType
	) {
		Page<ReviewProductResDto> reviewList = reviewService.getProductReviewListSorted(product_id, page, pageSize,
			sortType);
		return ResponseEntity.ok(reviewList);
	}

	@GetMapping("/{productId}/photo")
	@Operation(summary = "단일 상품 포토 리뷰 조회", description = "단일 상품에 대한 포토 리뷰를 조회합니다.")
	public ResponseEntity<Page<ReviewPhotoResDto>> getProductPhotoReviewList(
		@PathVariable Long product_id,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "8") int pageSize
	) {
		Page<ReviewPhotoResDto> reviewPhotoList = reviewService.getProductPhotoReviewList(product_id, page, pageSize);
		return ResponseEntity.ok(reviewPhotoList);
	}

	@GetMapping("/{reviewId}")
	@Operation(summary = "특정 리뷰 조회", description = "특정 리뷰를 한개를 조회합니다.")
	public ResponseEntity<ReviewProductResDto> getReviewDetails(
		@PathVariable Long review_id
	) {
		ReviewProductResDto reviewDetails = reviewService.getReviewDetails(review_id);
		return ResponseEntity.ok(reviewDetails);
	}

	@GetMapping("/{productId}/number")
	@Operation(summary = "리뷰 개수 조회", description = "특정 상품 리뷰를 전체 개수, 이미지 또는 동영상 리뷰 개수 조회합니다.")
	public ResponseEntity<ReviewCountResDto> getReviewCountList(
		@PathVariable Long product_id
	) {
		ReviewCountResDto countList = reviewService.getReviewCountList(product_id);
		return ResponseEntity.ok(countList);
	}

	@PutMapping("/{reviewId}")
	@Operation(summary = "리뷰 수정", description = "작성한 리뷰를 수정합니다.")
	public ResponseEntity<Void> updateReview(
		@RequestHeader("Authorization") String token,
		@PathVariable Long review_id,
		@RequestBody ReviewReqDto reviewReqDto
	) {
		String uuid = jwtProvider.getUuid(token);
		reviewService.updateReviewContents(uuid, review_id, reviewReqDto);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("{reviewId}")
	@Operation(summary = "리뷰 삭제", description = "해당 리뷰를 삭제합니다.")
	public ResponseEntity<Void> deleteReview(
		@RequestHeader("Authorization") String token,
		@PathVariable Long review_id
	) {
		String uuid = jwtProvider.getUuid(token);
		reviewService.deleteReview(uuid, review_id);

		return ResponseEntity.ok().build();
	}

}
