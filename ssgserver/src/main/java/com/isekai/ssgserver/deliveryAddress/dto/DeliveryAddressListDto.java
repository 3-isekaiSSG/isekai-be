package com.isekai.ssgserver.deliveryAddress.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DeliveryAddressListDto {

	private int id;
	private Long deliveryAddressId;
}
