package com.isekai.ssgserver.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DeliveryDetailDto {
	private int statusCode;
	private String statusName;
	private byte deliveryType;
	private String seller;
	private int originPrice;
	private int buyPrice;
	private int deliveryFee;
	private String deliveryCompany;
	private String deliveryCode;
	private String deliveryMessage;

	// address
	private Long deliveryAddressId;
}
