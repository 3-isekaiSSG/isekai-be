package com.isekai.ssgserver.order.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class NonMemberOrderDto {

	private String orderName;
	private String orderPhone;
	private String orderEmail;
	private String zipCode;
	private String address;

	private String deliveryMessage;
	private String payMethod;
	private String card;
	private int installment;

	private List<OrderProductDto> orderProductList;
}
