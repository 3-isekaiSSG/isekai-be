package com.isekai.ssgserver.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductSortOption {

	best("best", "추천순", true),
	prcasc("prcasc", "가격낮은순", false),
	prcdsc("prcdsc", "가격높은순", false),
	sale("sale", "판매순", false),
	dcrt("dcrt", "할인율순", false),
	regdt("regdt", "신상품순", false),
	cnt("cnt", "리뷰많은순", false);

	private final String code;
	private final String description;
	private final Boolean isInfo;

}
