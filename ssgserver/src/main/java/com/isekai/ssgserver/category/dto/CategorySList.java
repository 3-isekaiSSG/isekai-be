package com.isekai.ssgserver.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategorySList {

	private int id;
	private Long categoryId;
	private String name;
}
