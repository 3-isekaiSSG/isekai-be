package com.isekai.ssgserver.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryLResponseDto {

	private int id;
	private Long categoryLId;
	private String largeName;
	private String largeImg;
}
