package com.isekai.ssgserver.delivery.dto;

import com.isekai.ssgserver.delivery.entity.DeliveryType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryTypeDto {

	@Getter
	@Builder
	public static class Response {
		private Long deliveryTypeId;
		private String name;
	}

	public static Response mapDeliveryTypeDto(DeliveryType deliveryType) {
		return Response.builder()
			.deliveryTypeId(deliveryType.getDeliveryTypeId())
			.name(deliveryType.getName())
			.build();
	}

}
