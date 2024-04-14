package com.isekai.ssgserver.seller.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BrandNameSortOption {

	best("best", "인기순"),
	abc("abc", "가나다순");

	private final String code;
	private final String description;
}

