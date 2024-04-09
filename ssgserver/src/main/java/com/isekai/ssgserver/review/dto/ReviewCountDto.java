package com.isekai.ssgserver.review.dto;

import lombok.Getter;

@Getter
public class ReviewCountDto {
	private String id;
	private Long data;

	public ReviewCountDto(String id, Long data) {
		this.id = id;
		this.data = data;
	}
}
