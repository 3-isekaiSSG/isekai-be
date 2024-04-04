package com.isekai.ssgserver.cart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartInfoDto {

	private Long id;
	private String code;
	private Integer count;
	private byte checked;
	private Long optionId;

}
