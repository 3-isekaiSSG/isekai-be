package com.isekai.ssgserver.cart.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.isekai.ssgserver.cart.dto.CartAddDropDto;
import com.isekai.ssgserver.cart.dto.CartCountResponseDto;
import com.isekai.ssgserver.cart.dto.CartRequestDto;
import com.isekai.ssgserver.cart.dto.CartResponseDto;
import com.isekai.ssgserver.cart.service.CartService;
import com.isekai.ssgserver.cart.service.CartUpdateService;
import com.isekai.ssgserver.util.jwt.AuthDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/carts")
@Tag(name = "Cart", description = "장바구니 관련 API document")
public class CartController {

	private final CartService cartService;
	private final CartUpdateService cartUpdateService;

	// 장바구니 조회
	@GetMapping
	@Operation(summary = "장바구니 조회", description = "회원, 비회원 장바구니 조회")
	public ResponseEntity<CartResponseDto> getCart(HttpServletRequest request, HttpServletResponse response) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 인증된 사용자인 경우
		if (authentication != null && authentication.isAuthenticated()
			&& !(authentication instanceof AnonymousAuthenticationToken)) {
			AuthDto authDto = (AuthDto)authentication.getPrincipal();
			String uuid = authDto.getId();

			// 회원 장바구니 조회 로직 수행
			CartResponseDto cartDto = cartService.getMemberCart(uuid);
			return ResponseEntity.ok(cartDto);
		} else {
			// 비회원의 경우, 쿠키를 사용하여 장바구니 식별
			String cartValue = getOrCreateCartValue(request, response);
			// 비회원 장바구니 조회 로직 수행
			CartResponseDto cartDto = cartService.getNonMemberCart(cartValue);
			return ResponseEntity.ok(cartDto);
		}

	}

	// @GetMapping("/options/{optionId}")
	// @Operation(summary = "장바구니 상품 옵션값", description = "optionId를 통해 해당 상품의 옵션값을 내려줍니다.")
	// public ResponseEntity<CartOptionDto> getCartOption(@PathVariable Long optionId) {
	//
	// }

	@PostMapping
	@Operation(summary = "장바구니 담기", description = "해당 상품을 옵션과 함께 저장합니다.")
	public ResponseEntity<?> addCart(@RequestBody CartRequestDto cartRequestDto, HttpServletRequest request,
		HttpServletResponse response) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()
			&& !(authentication instanceof AnonymousAuthenticationToken)) {
			AuthDto authDto = (AuthDto)authentication.getPrincipal();
			String uuid = authDto.getId();

			return cartService.addMemberCartProduct(cartRequestDto, uuid);
		} else {
			String cartValue = getOrCreateCartValue(request, response);

			return cartService.addNonMemberCartProduct(cartRequestDto, cartValue);
		}

	}

	@GetMapping("/count")
	@Operation(summary = "장바구니 총 개수", description = "장바구니에 담겨있는 상품 종류의 개수입니다.")
	public ResponseEntity<?> getCartCount(HttpServletRequest request, HttpServletResponse response) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()
			&& !(authentication instanceof AnonymousAuthenticationToken)) {
			AuthDto authDto = (AuthDto)authentication.getPrincipal();
			String uuid = authDto.getId();

			CartCountResponseDto cartCountResponse = cartService.getMemberCartCount(uuid);

			return ResponseEntity.ok(cartCountResponse);
		} else {
			String cartValue = getOrCreateCartValue(request, response);

			CartCountResponseDto cartCountResponse = cartService.getNonMemberCartCount(cartValue);

			return ResponseEntity.ok(cartCountResponse);
		}
	}

	@PatchMapping("/one-add/{cartId}")
	@Operation(summary = "상품 개수 추가", description = "장바구니 + 버튼 누를시 상품 개수가 추가됩니다.")
	public CartAddDropDto addCountOne(@PathVariable Long cartId) {

		return cartUpdateService.addCountOne(cartId);
	}

	@PatchMapping("/one-drop/{cartId}")
	@Operation(summary = "상품 개수 차감", description = "장바구니 - 버튼 누를시 상품 개수가 차감됩니다.")
	public CartAddDropDto dropCountOne(@PathVariable Long cartId) {

		return cartUpdateService.dropCountOne(cartId);
	}


	private String getOrCreateCartValue(HttpServletRequest request, HttpServletResponse response) {

		// 쿠키에서 장바구니 value를 찾음
		Cookie cartCookie = WebUtils.getCookie(request, "CART_VALUE");
		if (cartCookie != null) {
			return cartCookie.getValue();
		} else {
			// 쿠키가 없는 경우, 새 장바구니 ID 생성 및 쿠키에 저장
			String newCartValue = UUID.randomUUID().toString();
			Cookie newCookie = new Cookie("CART_VALUE", newCartValue);
			newCookie.setPath("/");
			newCookie.setHttpOnly(true);
			newCookie.setMaxAge(24 * 60 * 60); // 쿠키 유효기간 1일 설정
			response.addCookie(newCookie);
			return newCartValue;
		}
	}

}
