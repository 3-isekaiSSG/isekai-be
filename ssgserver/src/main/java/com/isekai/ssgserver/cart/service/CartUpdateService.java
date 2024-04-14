package com.isekai.ssgserver.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.cart.dto.CartUpdateDto;
import com.isekai.ssgserver.cart.entity.Cart;
import com.isekai.ssgserver.cart.repository.CartRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.option.entity.Option;
import com.isekai.ssgserver.option.repository.OptionRepository;
import com.isekai.ssgserver.util.jwt.JwtProvider;
import com.isekai.ssgserver.util.jwt.JwtToken;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartUpdateService {

	private final CartRepository cartRepository;
	private final OptionRepository optionRepository;

	private final JwtProvider jwtProvider;

	// 비회원 장바구니 -> 회원 장바구니
	@Transactional
	public void updateCartByUuid(JwtToken tokens, String cartValue) {
		String uuid = jwtProvider.getUuid(tokens.getAccessToken()); // JWT 토큰에서 uuid 추출
		List<Cart> nonMemberCarts = cartRepository.findByCartValue(cartValue); // 비회원 장바구니 조회

		for (Cart cart : nonMemberCarts) {
			cart.updateUuid(uuid); // 각 장바구니 항목의 uuid를 회원의 uuid로 변경
		}

		cartRepository.saveAll(nonMemberCarts);
	}

	public CartUpdateDto updateCartProductOption(Long cartId, Long optionsId) {

		Cart cart = getCart(cartId);
		Option option = optionRepository.findById(optionsId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		cartRepository.save(Cart.builder()
			.cartId(cartId)
			.uuid(cart.getUuid())
			.cartValue(cart.getCartValue())
			.option(option)
			.count(cart.getCount())
			.checked(cart.getChecked())
			.build());

		return new CartUpdateDto(cart);
	}

	public CartUpdateDto addCountOne(Long cartId) {

		Cart cart = getCart(cartId);

		cartRepository.save(Cart.builder()
			.cartId(cartId)
			.uuid(cart.getUuid())
			.cartValue(cart.getCartValue())
			.option(cart.getOption())
			.count(cart.getCount() + 1)
			.checked(cart.getChecked())
			.build());

		return new CartUpdateDto(cart);
	}

	public CartUpdateDto dropCountOne(Long cartId) {

		Cart cart = getCart(cartId);

		cartRepository.save(Cart.builder()
			.cartId(cartId)
			.uuid(cart.getUuid())
			.cartValue(cart.getCartValue())
			.option(cart.getOption())
			.count(cart.getCount() - 1)
			.checked(cart.getChecked())
			.build());

		return new CartUpdateDto(cart);
	}

	public CartUpdateDto updateCheck(Long cartId) {

		Cart cart = getCart(cartId);

		cartRepository.save(Cart.builder()
			.cartId(cartId)
			.uuid(cart.getUuid())
			.cartValue(cart.getCartValue())
			.option(cart.getOption())
			.count(cart.getCount())
			.checked((byte)1)
			.build());

		return new CartUpdateDto(cart);
	}

	public CartUpdateDto updateUncheck(Long cartId) {

		Cart cart = getCart(cartId);

		cartRepository.save(Cart.builder()
			.cartId(cartId)
			.uuid(cart.getUuid())
			.cartValue(cart.getCartValue())
			.option(cart.getOption())
			.count(cart.getCount())
			.checked((byte)0)
			.build());

		return new CartUpdateDto(cart);
	}

	private Cart getCart(Long cartId) {

		return cartRepository.findById(cartId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}
}
