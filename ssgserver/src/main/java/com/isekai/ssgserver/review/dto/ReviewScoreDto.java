package com.isekai.ssgserver.review.dto;

import com.isekai.ssgserver.review.entity.ReviewScore;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewScoreDto {

	@Getter
	@Builder
	public static class Response {
		private Long reviewScoreId;
		private Long reviewCount;
		private Double avgScore;
	}

	public static Response mapReviewScoreDto(ReviewScore reviewScore) {
		return Response.builder()
			.reviewScoreId(reviewScore.getReviewScoreId())
			.reviewCount(reviewScore.getReviewCount())
			.avgScore(reviewScore.getAvgScore())
			.build();
	}

}
