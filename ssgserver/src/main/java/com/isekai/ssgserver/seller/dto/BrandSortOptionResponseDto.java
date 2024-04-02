package com.isekai.ssgserver.seller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandSortOptionResponseDto {

	private Integer id;
	private String option;
	private String value;
}
