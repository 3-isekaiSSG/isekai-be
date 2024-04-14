package com.isekai.ssgserver.bundle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BundleCardResDto {
	private Long bundleId;
	private String outerName;
	private String code;
	private Integer minPrice;
	private Long buyCount;
	private String imgUrl;
}
