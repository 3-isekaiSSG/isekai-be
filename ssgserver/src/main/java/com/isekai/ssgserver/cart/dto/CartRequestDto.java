package com.isekai.ssgserver.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequestDto {

	private Long optionsId;
	private Integer count;
}
