package com.isekai.ssgserver.cart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartCountResponseDto {

	private Integer id;
	private Integer cnt;
}
