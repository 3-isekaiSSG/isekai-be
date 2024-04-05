package com.isekai.ssgserver.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OrderProductDetailDto {

	private String productCode;
	private int count;
	private int buyPrice;
	private int originPrice;
	private boolean isConfirm;

}
