package com.isekai.ssgserver.category.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryMResponseDto {

	private int id;
	private Long categoryLId;

	private List<CategoryMList> categoryMList;

}
