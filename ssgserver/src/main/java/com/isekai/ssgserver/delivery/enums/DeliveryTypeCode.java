package com.isekai.ssgserver.delivery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryTypeCode {

	COURIER(1, "택배배송", null),
	SSG_DELIVERY(2, "쓱배송", "emart"),
	MORNING_DELIVERY(3, "새벽배송", "earlymorning"),
	TRADERS_SSG_DELIVERY(4, "트레이더스 쓱배송", "traders"),
	DEPARTMENT_STORE(5, "백화점 상품", "department");

	private final int code;
	private final String name;
	private final String engName;

	public static String getNameByCode(int code) {
		for (DeliveryTypeCode type : DeliveryTypeCode.values()) {
			if (type.getCode() == code) {
				return type.getName();
			}
		}
		return null;
	}
}
