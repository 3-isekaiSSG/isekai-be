package com.isekai.ssgserver.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProductInfoDto {

	private Integer id;
	private String code;
}
