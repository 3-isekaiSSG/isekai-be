package com.isekai.ssgserver.order.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MemberOrderDto {

	private String orderName;
	private String orderPhone;
	private String orderEmail;
	private Long deliveryAddressId;

	private String deliveryMessage;
	private String payMethod;
	private String card;
	private int installment;

	private int originPrice;
	private int deliveryFee;
	private int buyPrice;
	private int discountPrice;

	private List<OrderSellerProductDto> orderSellerProductDtoList;
}