package com.isekai.ssgserver.bundle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BundleListResDto {
	private Long id;
	private Long bundleId;
	private String code;

	public void setBundleId(Long bundleId) {
		this.bundleId = bundleId;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
