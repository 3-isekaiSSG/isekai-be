package com.isekai.ssgserver.review.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewPhotoResDto {
	private Long reviewId;
	private String reviewImage;
	private Long productId;
	private LocalDateTime createdAt;
}