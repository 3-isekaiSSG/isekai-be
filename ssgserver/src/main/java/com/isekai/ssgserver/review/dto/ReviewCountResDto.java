package com.isekai.ssgserver.review.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewCountResDto {
	private List<ReviewCountDto> reviewCountList;
}
