package com.isekai.ssgserver.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryMList {

	private int id;
	private Long categoryId;
	private String name;
	private boolean isColored;
	private String img;
}
