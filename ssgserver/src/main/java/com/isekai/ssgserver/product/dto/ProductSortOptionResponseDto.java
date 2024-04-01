package com.isekai.ssgserver.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductSortOptionResponseDto {

	private Integer id;
	private String option;
	private Boolean isInfo;
}
