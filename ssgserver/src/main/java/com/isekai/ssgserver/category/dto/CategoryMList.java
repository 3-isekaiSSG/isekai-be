package com.isekai.ssgserver.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryMList {

	private int id;
	private Long categoryMId;
	private String mediumName;
	private boolean isColored;
}
