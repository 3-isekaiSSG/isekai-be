package com.isekai.ssgserver.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class DeliveryListDto {

	private int id;
	private Long deliveryId;
}
