package com.isekai.ssgserver.category.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDto {

	private int id;
	private Long categoryLId;
	private String largeName;

	private List<CategoryMList> categoryMList;
}
