package com.isekai.ssgserver.bundle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BundleProductListResDto {
	private Long bundleProductId;
	private String productCode;
	private Long bundleId;
}
