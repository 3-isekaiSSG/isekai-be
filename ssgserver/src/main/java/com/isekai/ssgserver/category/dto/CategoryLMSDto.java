package com.isekai.ssgserver.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryLMSDto {

	private CategoryCommonDto largeName;
	private CategoryCommonDto mediumName;
	private CategoryCommonDto smallName;
}
