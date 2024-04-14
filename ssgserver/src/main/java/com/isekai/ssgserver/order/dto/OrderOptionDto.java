package com.isekai.ssgserver.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class OrderOptionDto {

	private int depth;
	private Long optionId;
	private String optionName;
}
