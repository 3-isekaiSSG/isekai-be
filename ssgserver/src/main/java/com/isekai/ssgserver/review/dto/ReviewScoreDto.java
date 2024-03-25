package com.isekai.ssgserver.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewScoreDto {

	// private Long id;
	private Long reviewCount;
	private Double avgScore;
}
