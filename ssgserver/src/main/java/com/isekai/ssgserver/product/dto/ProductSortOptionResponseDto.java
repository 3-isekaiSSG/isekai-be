package com.isekai.ssgserver.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductSortOptionResponseDto {

	private Integer id;
	private String option;
	private Boolean isInfo;
}
