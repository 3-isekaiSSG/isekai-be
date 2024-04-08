package com.isekai.ssgserver.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderListDto {

	private Integer id;
	private Long ordersId;
	private String code;
	private Integer buyPrice;
	private String date;
}
