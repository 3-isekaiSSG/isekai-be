package com.isekai.ssgserver.cart.dto;

import com.isekai.ssgserver.cart.entity.Cart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartUpdateDto {

	private Long cartId;
	private String uuid;
	private String cartValue;
	private Long optionId;
	private Integer count;
	private byte checked;

	public CartUpdateDto(Cart cart) {

		this.cartId = cart.getCartId();
		this.uuid = cart.getUuid();
		this.cartValue = cart.getCartValue();
		this.optionId = cart.getOption().getOptionsId();
		this.count = cart.getCount();
		this.checked = cart.getChecked();
	}
}
