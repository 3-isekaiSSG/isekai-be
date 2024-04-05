package com.isekai.ssgserver.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OrderSummaryDto {

	private String date;
	private Long orderId;
	private int buyPrice;

}
