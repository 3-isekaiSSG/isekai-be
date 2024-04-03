package com.isekai.ssgserver.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FavoriteDivision {
	SINGLE_PRODUCT((byte)0, "단일상품"),
	BUNDLE_PRODUCT((byte)1, "묶음상품"),
	CATEGORYL((byte)2, "카테고리L"),
	CATEGORYM((byte)3, "카테고리M"),
	BRAND((byte)4, "브랜드");

	private final byte code;
	private final String description;
}