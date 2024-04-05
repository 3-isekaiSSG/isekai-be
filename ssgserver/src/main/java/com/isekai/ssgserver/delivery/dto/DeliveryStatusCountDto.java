package com.isekai.ssgserver.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DeliveryStatusCountDto {

	private int status;
	private String statusName;
	private int count;
}
