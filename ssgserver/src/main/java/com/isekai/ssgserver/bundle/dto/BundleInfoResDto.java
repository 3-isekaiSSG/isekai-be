package com.isekai.ssgserver.bundle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BundleInfoResDto {
	private Long bundleId;
	private String innerName;
	private String code;
	private Integer minPrice;
	private Long reviewCount;
	private double avgScore;
	private Long buyCount;
}
