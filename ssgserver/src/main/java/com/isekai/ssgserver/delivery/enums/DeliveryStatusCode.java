package com.isekai.ssgserver.delivery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatusCode {

	ORDER_RECEIVED(0, "주문접수"),
	PAY_COMPLETED(1, "결제완료"),
	PRODUCT_PREPARING(2, "상품준비중"),
	DELIVERY_PREPARING(3, "배송준비중"),
	DELIVERY_IN_PROGRESS(4, "배송중"),
	DELIVERY_COMPLETED(5, "배송완료"),
	PURCHASE_COMPLETED(6, "구매확정");

	private final int code;
	private final String name;

	public static String getNameByCode(int code) {
		for (DeliveryStatusCode status : DeliveryStatusCode.values()) {
			if (status.getCode() == code) {
				return status.getName();
			}
		}
		return null;
	}
}
