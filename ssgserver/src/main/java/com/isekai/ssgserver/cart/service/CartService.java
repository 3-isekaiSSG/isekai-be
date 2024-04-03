package com.isekai.ssgserver.cart.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.cart.dto.CartInfoDto;
import com.isekai.ssgserver.cart.dto.CartResponseDto;
import com.isekai.ssgserver.cart.repository.CartRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;

	public CartResponseDto getMemberCart(String uuid) {

		List<CartInfoDto> cartInfoDtoNormal = cartRepository.findCartByUuidAndDeliveryType(uuid, (byte)1);
		List<CartInfoDto> cartInfoDtoSsg = cartRepository.findCartByUuidAndDeliveryType(uuid, (byte)0);
		AtomicInteger normalId = new AtomicInteger(0);
		AtomicInteger ssgId = new AtomicInteger(0);

		return null;
	}

	public CartResponseDto getNonMemberCart(String cartValue) {

		return null;
	}
}
