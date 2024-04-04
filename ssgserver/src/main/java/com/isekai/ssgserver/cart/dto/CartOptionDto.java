package com.isekai.ssgserver.cart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartOptionDto {

	private Integer depth;
	private String category;
	private String value;
}
