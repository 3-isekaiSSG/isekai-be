package com.isekai.ssgserver.cart.service;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.cart.dto.CartAddDropDto;
import com.isekai.ssgserver.cart.entity.Cart;
import com.isekai.ssgserver.cart.repository.CartRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartUpdateService {

	private final CartRepository cartRepository;

	public CartAddDropDto addCountOne(Long cartId) {

		Cart cart = getCart(cartId);

		cartRepository.save(Cart.builder()
			.cartId(cartId)
			.uuid(cart.getUuid())
			.cartValue(cart.getCartValue())
			.option(cart.getOption())
			.count(cart.getCount() + 1)
			.checked(cart.getChecked())
			.build());

		return new CartAddDropDto(cart);
	}

	public CartAddDropDto dropCountOne(Long cartId) {

		Cart cart = getCart(cartId);

		cartRepository.save(Cart.builder()
			.cartId(cartId)
			.uuid(cart.getUuid())
			.cartValue(cart.getCartValue())
			.option(cart.getOption())
			.count(cart.getCount() - 1)
			.checked(cart.getChecked())
			.build());

		return new CartAddDropDto(cart);
	}

	private Cart getCart(Long cartId) {

		return cartRepository.findById(cartId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}
}
