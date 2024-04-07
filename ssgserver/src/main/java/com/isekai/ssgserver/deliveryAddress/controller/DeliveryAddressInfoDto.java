package com.isekai.ssgserver.deliveryAddress.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class DeliveryAddressInfoDto {

	private String nickname;
	private String name;
	private String cellphone;
	private String telephone;
	private String zipcode;
	private String address;
	private boolean isDefault;
	private boolean isDeleted;
	private boolean orderHistory;
}
