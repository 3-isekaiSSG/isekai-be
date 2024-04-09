package com.isekai.ssgserver.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isekai.ssgserver.cart.dto.CartCountResponseDto;
import com.isekai.ssgserver.cart.dto.CartInfoDto;
import com.isekai.ssgserver.cart.dto.CartOptionDto;
import com.isekai.ssgserver.cart.dto.CartRequestDto;
import com.isekai.ssgserver.cart.dto.CartResponseDto;
import com.isekai.ssgserver.cart.entity.Cart;
import com.isekai.ssgserver.cart.repository.CartRepository;
import com.isekai.ssgserver.delivery.repository.ProductDeliveryTypeRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.option.entity.Option;
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
	// 회원
	@Transactional
	public CartResponseDto getMemberCart(String uuid) {

		List<Cart> carts = cartRepository.findByUuidOrderByCreatedAtDesc(uuid);
		Integer cnt = cartRepository.countByUuid(uuid);
		List<CartInfoDto> postItems = new ArrayList<>();
		List<CartInfoDto> ssgItems = new ArrayList<>();

		AtomicInteger postId = new AtomicInteger(0);
		AtomicInteger ssgId = new AtomicInteger(0);

		for (Cart cart : carts) {
			// productCode를 기반으로 DeliveryType 찾기
			productDeliveryTypeRepository.findByProductCode(cart.getOption().getProductCode())
				.ifPresent(productDeliveryType -> {
					CartInfoDto itemDTO = CartInfoDto.builder()
						.id("ssg".equalsIgnoreCase(productDeliveryType.getDeliveryType().getEngName()) ?
							ssgId.getAndIncrement() : postId.getAndIncrement()) // 조건에 따라 ID 할당
						.code(cart.getOption().getProductCode()) // `Option`을 통해 `productCode` 접근
						.count(cart.getCount())
						.checked(cart.getChecked())
						.optionId(cart.getOption().getOptionsId())
						.build();

					if ("ssg".equalsIgnoreCase(productDeliveryType.getDeliveryType().getEngName())) {
						ssgItems.add(itemDTO);
					} else {
						postItems.add(itemDTO);
					}
				});
		}

		CartResponseDto cartResponse = CartResponseDto.builder()
			.id(0)
			.cnt(cnt)
			.post(postItems)
			.ssg(ssgItems)
			.build();
		return cartResponse;
	}

	// 비회원
	@Transactional
	public CartResponseDto getNonMemberCart(String cartValue) {

		List<Cart> carts = cartRepository.findByCartValueOrderByCreatedAtDesc(cartValue);
		Integer cnt = cartRepository.countByCartValue(cartValue);
		List<CartInfoDto> postItems = new ArrayList<>();
		List<CartInfoDto> ssgItems = new ArrayList<>();

		AtomicInteger postId = new AtomicInteger(0);
		AtomicInteger ssgId = new AtomicInteger(0);

		for (Cart cart : carts) {
			// productCode를 기반으로 DeliveryType 찾기
			productDeliveryTypeRepository.findByProductCode(cart.getOption().getProductCode())
				.ifPresent(productDeliveryType -> {
					CartInfoDto itemDTO = CartInfoDto.builder()
						.id("ssg".equalsIgnoreCase(productDeliveryType.getDeliveryType().getEngName()) ?
							ssgId.getAndIncrement() : postId.getAndIncrement()) // 조건에 따라 ID 할당
						.cartId(cart.getCartId())
						.code(cart.getOption().getProductCode()) // `Option`을 통해 `productCode` 접근
						.count(cart.getCount())
						.checked(cart.getChecked())
						.optionId(cart.getOption().getOptionsId())
						.build();

					if ("ssg".equalsIgnoreCase(productDeliveryType.getDeliveryType().getEngName())) {
						ssgItems.add(itemDTO);
					} else {
						postItems.add(itemDTO);
					}
				});
		}

		CartResponseDto cartResponse = CartResponseDto.builder()
			.id(0)
			.cnt(cnt)
			.post(postItems)
			.ssg(ssgItems)
			.build();
		return cartResponse;
	}

	// 장바구니 담기
	// 회원
	@Transactional
	public ResponseEntity<Void> addMemberCartProduct(List<CartRequestDto> cartRequestDtos, String uuid) {

		List<Cart> carts = cartRepository.findByUuid(uuid);

		for (CartRequestDto cartRequestDto : cartRequestDtos) {
			Option option = optionRepository.findById(cartRequestDto.getOptionsId())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

			Cart cart = carts.stream()
				.filter(c -> c.getOption().getOptionsId().equals(cartRequestDto.getOptionsId()))
				.findFirst()
				.orElse(null);

			if (cart != null) {
				cartRepository.save(Cart.builder()
					.cartId(cart.getCartId())
					.uuid(uuid)
					.cartValue(cart.getCartValue())
					.option(option)
					.count(cart.getCount() + cartRequestDto.getCount())
					.checked(cart.getChecked())
					.build());
			} else {
				cartRepository.save(Cart.builder()
					.uuid(uuid)
					.option(option)
					.count(cartRequestDto.getCount())
					.checked((byte)0)
					.build());
			}
		}
		return ResponseEntity.ok().build();

	}

	// 비회원
	@Transactional
	public ResponseEntity<Void> addNonMemberCartProduct(List<CartRequestDto> cartRequestDtos, String cartValue) {

		List<Cart> carts = cartRepository.findByCartValue(cartValue);

		for (CartRequestDto cartRequestDto : cartRequestDtos) {
			Option option = optionRepository.findById(cartRequestDto.getOptionsId())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

			Cart cart = carts.stream()
				.filter(c -> c.getOption().getOptionsId().equals(cartRequestDto.getOptionsId()))
				.findFirst()
				.orElse(null);

			if (cart != null) {
				cartRepository.save(Cart.builder()
					.cartId(cart.getCartId())
					.uuid("-1")
					.cartValue(cart.getCartValue())
					.option(option)
					.count(cart.getCount() + cartRequestDto.getCount())
					.checked(cart.getChecked())
					.build());
			} else {
				cartRepository.save(Cart.builder()
					.uuid("-1")
					.cartValue(cartValue)
					.option(option)
					.count(cartRequestDto.getCount())
					.checked((byte)0)
					.build());
			}
		}
		return ResponseEntity.ok().build();
	}

	// 장바구니 상품 옵션
	public List<CartOptionDto> getCartProductOption(Long optionsId) {
		List<CartOptionDto> cartOptionDtos = new ArrayList<>();
		AtomicInteger id = new AtomicInteger(0);

		findOptionsRecursively(optionsId, cartOptionDtos, id);
		return cartOptionDtos;
	}

	// 장바구니 총 개수
	// 회원
	public CartCountResponseDto getMemberCartCount(String uuid) {

		Integer cnt = cartRepository.countByUuid(uuid);

		return CartCountResponseDto.builder()
			.cnt(cnt)
			.build();
	}

	// 비회원
	public CartCountResponseDto getNonMemberCartCount(String cartValue) {

		Integer cnt = cartRepository.countByCartValue(cartValue);

		return CartCountResponseDto.builder()
			.cnt(cnt)
			.build();
	}

	// 장바구니 한개 삭제
	public ResponseEntity<Void> deleteOneProduct(Long cartId) {

		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		cartRepository.delete(cart);

		return ResponseEntity.ok().build();
	}

	// 장바구니 선택 삭제
	public ResponseEntity<Void> deleteSelectProduct(List<Long> cartIds) {

		cartRepository.deleteAllById(cartIds);

		return ResponseEntity.ok().build();
	}

	// 옵션 조회
	private void findOptionsRecursively(Long optionsId, List<CartOptionDto> cartOptionDtos, AtomicInteger id) {
		Option option = optionRepository.findById(optionsId)
			.orElseThrow(() -> new RuntimeException("Option not found with id: " + optionsId));

		// 부모 옵션이 존재하는 경우 재귀적으로 탐색
		if (option.getParent() != null) {
			findOptionsRecursively(option.getParent().getOptionsId(), cartOptionDtos, id);
		}

		CartOptionDto cartOptionDto = CartOptionDto.builder()
			.id(id.getAndIncrement())
			.optionsId(option.getOptionsId())
			.value(option.getValue())
			.category(option.getCategory())
			.depth(option.getDepth())
			.build();
		cartOptionDtos.add(cartOptionDto);

	}

}
