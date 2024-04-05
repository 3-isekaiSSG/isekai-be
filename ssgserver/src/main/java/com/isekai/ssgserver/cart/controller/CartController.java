package com.isekai.ssgserver.cart.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.isekai.ssgserver.cart.dto.CartCountResponseDto;
import com.isekai.ssgserver.cart.dto.CartOptionDto;
import com.isekai.ssgserver.cart.dto.CartRequestDto;
import com.isekai.ssgserver.cart.dto.CartResponseDto;
import com.isekai.ssgserver.cart.dto.CartUpdateDto;
import com.isekai.ssgserver.cart.service.CartService;
import com.isekai.ssgserver.cart.service.CartUpdateService;
import com.isekai.ssgserver.util.jwt.JwtProvider;

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

	private final JwtProvider jwtProvider;
	private final CartService cartService;
	private final CartUpdateService cartUpdateService;

	// 장바구니 조회
	@GetMapping("/member")
	@Operation(summary = "회원 장바구니 조회", description = "헤더에 토큰 필요 회원 장바구니 조회")
	public ResponseEntity<CartResponseDto> getMemberCart(@RequestHeader("Authorization") String token) {

		String uuid = jwtProvider.getUuid(token);

		CartResponseDto cartResponse = cartService.getMemberCart(uuid);

		return ResponseEntity.ok(cartResponse);

	}

	@GetMapping("/non-member")
	@Operation(summary = "비회원 장바구니 조회", description = "비회원 장바구니 조회")
	public ResponseEntity<CartResponseDto> getNonMemberCart(HttpServletRequest request, HttpServletResponse response) {

		// 비회원의 경우, 쿠키를 사용하여 장바구니 식별
		String cartValue = getOrCreateCartValue(request, response);
		// 비회원 장바구니 조회 로직 수행
		CartResponseDto cartResponse = cartService.getNonMemberCart(cartValue);
		return ResponseEntity.ok(cartResponse);
	}

	@PostMapping("/member")
	@Operation(summary = "회원 장바구니 담기", description = "해당 상품을 옵션과 함께 저장합니다.")
	public ResponseEntity<Void> addMemberCart(@RequestBody CartRequestDto cartRequestDto,
		@RequestHeader("Authorization") String token) {

		String uuid = jwtProvider.getUuid(token);

		return cartService.addMemberCartProduct(cartRequestDto, uuid);
	}

	@PostMapping("/non-member")
	@Operation(summary = "비회원 장바구니 담기", description = "해당 상품을 옵션과 함께 저장합니다.")
	public ResponseEntity<Void> addNonMemberCart(@RequestBody CartRequestDto cartRequestDto, HttpServletRequest request,
		HttpServletResponse response
	) {

		String cartValue = getOrCreateCartValue(request, response);

		return cartService.addNonMemberCartProduct(cartRequestDto, cartValue);
	}

	@GetMapping("/options/{optionsId}")
	@Operation(summary = "장바구니 상품 옵션값", description = "optionId를 통해 해당 상품의 옵션값을 내려줍니다.")
	public ResponseEntity<List<CartOptionDto>> getCartProductOption(@PathVariable Long optionsId) {

		List<CartOptionDto> cartOption = cartService.getCartProductOption(optionsId);

		return ResponseEntity.ok(cartOption);
	}

	@GetMapping("/member/count")
	@Operation(summary = "회원 장바구니 총 개수", description = "장바구니에 담겨있는 상품 종류의 개수입니다.")
	public ResponseEntity<CartCountResponseDto> getCartMemberCount(@RequestHeader("Authorization") String token) {

		String uuid = jwtProvider.getUuid(token);

		CartCountResponseDto cartCountResponse = cartService.getMemberCartCount(uuid);

		return ResponseEntity.ok(cartCountResponse);
	}

	@GetMapping("/non-member/count")
	@Operation(summary = "비회원 장바구니 총 개수", description = "장바구니에 담겨있는 상품 종류의 개수입니다.")
	public ResponseEntity<CartCountResponseDto> getCartNonMemberCount(HttpServletRequest request,
		HttpServletResponse response) {

		String cartValue = getOrCreateCartValue(request, response);

		CartCountResponseDto cartCountResponse = cartService.getNonMemberCartCount(cartValue);

		return ResponseEntity.ok(cartCountResponse);
	}

	@PatchMapping("/options/{cartId}")
	@Operation(summary = "장바구니 상품 옵션 수정", description = "장바구니에 있는 상품의 옵션을 수정합니다.")
	public CartUpdateDto updateCartProductOption(@PathVariable Long cartId, @RequestParam Long optionsId) {

		return cartUpdateService.updateCartProductOption(cartId, optionsId);
	}

	@PatchMapping("/one-add/{cartId}")
	@Operation(summary = "상품 개수 추가", description = "장바구니 + 버튼 누를시 상품 개수가 추가됩니다.")
	public CartUpdateDto addCountOne(@PathVariable Long cartId) {

		return cartUpdateService.addCountOne(cartId);
	}

	@PatchMapping("/one-drop/{cartId}")
	@Operation(summary = "상품 개수 차감", description = "장바구니 - 버튼 누를시 상품 개수가 차감됩니다.")
	public CartUpdateDto dropCountOne(@PathVariable Long cartId) {

		return cartUpdateService.dropCountOne(cartId);
	}

	@PatchMapping("/checked/{cartId}")
	@Operation(summary = "체크하기", description = "장바구니 체크하면 계속 유지되게 합니다.")
	public CartUpdateDto updateCheck(@PathVariable Long cartId) {

		return cartUpdateService.updateCheck(cartId);
	}

	@PatchMapping("/unchecked/{cartId}")
	@Operation(summary = "체크 취소하기", description = "장바구니에 체크되어 있던 것을 취소합니다.")
	public CartUpdateDto updateUncheck(@PathVariable Long cartId) {

		return cartUpdateService.updateUncheck(cartId);
	}

	@DeleteMapping("/one/{cartId}")
	@Operation(summary = "장바구니 상품 1개 삭제", description = "장바구니에 있는 상품 하나를 삭제합니다.")
	public ResponseEntity<Void> deleteOneProduct(@PathVariable Long cartId) {

		return cartService.deleteOneProduct(cartId);
	}

	@DeleteMapping
	@Operation(summary = "장바구니 선택 삭제", description = "장바구니의 선택 상품들을 삭제합니다.")
	public ResponseEntity<Void> deleteSelectProduct(@RequestParam List<Long> cartIds) {

		return cartService.deleteSelectProduct(cartIds);
	}

	// 품절 상품 삭제 논의 필요

	private String getOrCreateCartValue(HttpServletRequest request, HttpServletResponse response) {

		// 쿠키에서 장바구니 value를 찾음
		Cookie cartCookie = WebUtils.getCookie(request, "CART_VALUE");
		if (cartCookie != null) {
			cartCookie.setMaxAge(24 * 60 * 60 * 2);
			return cartCookie.getValue();
		} else {
			// 쿠키가 없는 경우, 새 장바구니 ID 생성 및 쿠키에 저장
			String newCartValue = UUID.randomUUID().toString();
			Cookie newCookie = new Cookie("CART_VALUE", newCartValue);
			newCookie.setPath("/");
			newCookie.setHttpOnly(true);
			newCookie.setMaxAge(24 * 60 * 60 * 2); // 쿠키 유효기간 2일 설정
			response.addCookie(newCookie);
			return newCartValue;
		}
	}

}
