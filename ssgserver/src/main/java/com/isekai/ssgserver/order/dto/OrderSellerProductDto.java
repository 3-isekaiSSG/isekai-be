package com.isekai.ssgserver.order.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OrderSellerProductDto {

	private String sellerName;
	private int deliveryFee;
	private byte delivertType;
	private List<OrderProductDto> orderProductList;
}
