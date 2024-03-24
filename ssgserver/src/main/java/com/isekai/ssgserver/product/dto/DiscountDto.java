package com.isekai.ssgserver.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiscountDto {

	private Long discountRate;
	private Long discountPrice;
}
