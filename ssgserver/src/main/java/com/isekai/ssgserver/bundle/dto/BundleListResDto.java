package com.isekai.ssgserver.bundle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BundleListResDto {
	private Long bundleId;
	private String code;
}
