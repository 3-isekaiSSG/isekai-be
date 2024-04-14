package com.isekai.ssgserver.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandNameResponseDto {

	private Integer id;
	private String name;
	private Long cnt;
}
