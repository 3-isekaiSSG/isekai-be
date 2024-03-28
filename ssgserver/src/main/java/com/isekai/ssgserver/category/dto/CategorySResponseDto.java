package com.isekai.ssgserver.category.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategorySResponseDto {

	private int id;
	private String mediumName;

	private List<CategorySList> categorySList;
}
