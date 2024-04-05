package com.isekai.ssgserver.cart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartOptionDto {

	private Integer id;
	private Long optionsId;
	private String value;
	private String category;
	private Integer depth;
}
