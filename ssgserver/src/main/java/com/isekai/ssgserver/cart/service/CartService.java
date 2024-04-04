package com.isekai.ssgserver.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.cart.dto.CartInfoDto;
import com.isekai.ssgserver.cart.dto.CartResponseDto;
import com.isekai.ssgserver.cart.entity.Cart;
import com.isekai.ssgserver.cart.repository.CartRepository;
import com.isekai.ssgserver.delivery.repository.ProductDeliveryTypeRepository;
import com.isekai.ssgserver.option.repository.OptionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	private final OptionRepository optionRepository;
	private final ProductDeliveryTypeRepository productDeliveryTypeRepository;

	// 장바구니 조회
	public CartResponseDto getMemberCart(String uuid) {

		List<Cart> carts = cartRepository.findByUuidOrderByCreatedAtDesc(uuid);
		List<CartInfoDto> normalItems = new ArrayList<>();
		List<CartInfoDto> ssgItems = new ArrayList<>();

		AtomicInteger normalId = new AtomicInteger(0);
		AtomicInteger ssgId = new AtomicInteger(0);

		for (Cart cart : carts) {
			// productCode를 기반으로 DeliveryType 찾기
			productDeliveryTypeRepository.findByProductCode(cart.getOption().getProductCode())
				.ifPresent(productDeliveryType -> {
					CartInfoDto itemDTO = CartInfoDto.builder()
						.id("ssg".equalsIgnoreCase(productDeliveryType.getDeliveryType().getEngName()) ?
							ssgId.getAndIncrement() : normalId.getAndIncrement()) // 조건에 따라 ID 할당
						.code(cart.getOption().getProductCode()) // `Option`을 통해 `productCode` 접근
						.count(cart.getCount())
						.checked(cart.getChecked())
						.optionId(cart.getOption().getOptionsId())
						.build();

					if ("ssg".equalsIgnoreCase(productDeliveryType.getDeliveryType().getEngName())) {
						ssgItems.add(itemDTO);
					} else {
						normalItems.add(itemDTO);
					}
				});
		}

		CartResponseDto cartResponse = CartResponseDto.builder()
			.id(0)
			.normal(normalItems)
			.ssg(ssgItems)
			.build();
		return cartResponse;
	}

	public CartResponseDto getNonMemberCart(String cartValue) {

		List<Cart> carts = cartRepository.findByCartValueOrderByCreatedAtDesc(cartValue);
		List<CartInfoDto> normalItems = new ArrayList<>();
		List<CartInfoDto> ssgItems = new ArrayList<>();

		AtomicInteger normalId = new AtomicInteger(0);
		AtomicInteger ssgId = new AtomicInteger(0);

		for (Cart cart : carts) {
			// productCode를 기반으로 DeliveryType 찾기
			productDeliveryTypeRepository.findByProductCode(cart.getOption().getProductCode())
				.ifPresent(productDeliveryType -> {
					CartInfoDto itemDTO = CartInfoDto.builder()
						.id("ssg".equalsIgnoreCase(productDeliveryType.getDeliveryType().getEngName()) ?
							ssgId.getAndIncrement() : normalId.getAndIncrement()) // 조건에 따라 ID 할당
						.code(cart.getOption().getProductCode()) // `Option`을 통해 `productCode` 접근
						.count(cart.getCount())
						.checked(cart.getChecked())
						.optionId(cart.getOption().getOptionsId())
						.build();

					if ("ssg".equalsIgnoreCase(productDeliveryType.getDeliveryType().getEngName())) {
						ssgItems.add(itemDTO);
					} else {
						normalItems.add(itemDTO);
					}
				});
		}

		CartResponseDto cartResponse = CartResponseDto.builder()
			.id(0)
			.normal(normalItems)
			.ssg(ssgItems)
			.build();
		return cartResponse;
	}

}
