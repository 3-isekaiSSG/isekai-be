package com.isekai.ssgserver.order.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OrderProductDto {

	private String productCode;
	private int originPrice;
	private int buyPrice;
	private int count;
	// 옵션
	private List<OrderOptionDto> orderOptionList;
}
