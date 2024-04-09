package com.isekai.ssgserver.review.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewProductResDto {
	private Long reviewId;
	private byte score;
	private String reviewContent;
	private String accountId;
	private Long productId;
	private String reviewImage;
	private LocalDateTime createdAt;

}
