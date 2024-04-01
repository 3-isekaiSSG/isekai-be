package com.isekai.ssgserver.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DeliveryTypeDto {

	private int id;
	private Long deliveryTypeId;
	private String name;
	private String imageUrl;
	private String selectedImageUrl;

}
