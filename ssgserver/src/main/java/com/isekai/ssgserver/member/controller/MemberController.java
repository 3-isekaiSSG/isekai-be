package com.isekai.ssgserver.member.controller;

import com.isekai.ssgserver.member.dto.SocialMappingDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.isekai.ssgserver.cart.service.CartUpdateService;
import com.isekai.ssgserver.member.dto.MemberJoinDto;
import com.isekai.ssgserver.member.dto.MemberLoginDto;
import com.isekai.ssgserver.member.dto.SocialMemberDto;
import com.isekai.ssgserver.member.service.MemberService;
import com.isekai.ssgserver.util.MessageResponse;
import com.isekai.ssgserver.util.jwt.JwtToken;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/auth")
@Tag(name = "Member", description = "회원가입, 로그인 등등 기본적으로 멤버 관련 필요한 메소드")
public class MemberController {

	private final MemberService memberService;
	private final CartUpdateService cartUpdateService;
	@Value("${jwt.token.refresh-expire-time}")
	private long refreshExpireTime;

	@PostMapping("/join")
	@Operation(summary = "회원가입")
	public ResponseEntity<String> join(@RequestBody MemberJoinDto joinDto) {
		String response = memberService.join(joinDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/duplication-id")
	@Operation(summary = "아이디 중복체크", description = "아이디가 중복되었다면 409 error, 아니라면 200 ok status와 메시지 반환")
	public ResponseEntity<MessageResponse> duplicationCheck(@RequestParam String inputId) {
		memberService.isDuplicate(inputId);
		return ResponseEntity.ok(new MessageResponse("사용 가능한 아이디입니다."));
	}

	@PostMapping("/login")
	@Operation(summary = "로그인")
	public ResponseEntity<JwtToken> login(@RequestBody MemberLoginDto loginDto, HttpServletRequest request,
		HttpServletResponse response) {
		JwtToken tokens = memberService.login(loginDto);

		// 비회원 장바구니 -> 회원 장바구니
		Cookie cartCookie = WebUtils.getCookie(request, "CART_VALUE");
		if (cartCookie != null) {
			String cartValue = cartCookie.getValue();
			cartUpdateService.updateCartByUuid(tokens, cartValue);
			cartCookie.setPath("/");
			cartCookie.setMaxAge(0);
			response.addCookie(cartCookie);
		}

		return ResponseEntity.ok(tokens);
	}

	@PostMapping("/social-login")
	@Operation(summary = "소셜 회원 확인", description = "소셜로 가입된 유저인지 아닌지 확인해서 맞다면 login 시키고, 아니라면 간편회원가입 페이지로 리다이렉트")
	public ResponseEntity<JwtToken> socialMember(@RequestBody SocialMemberDto socialMemberDto,
		HttpServletRequest request, HttpServletResponse response) {
		JwtToken tokens = memberService.socialLogin(socialMemberDto);

		// 비회원 장바구니 -> 회원 장바구니
		Cookie cartCookie = WebUtils.getCookie(request, "CART_VALUE");
		if (cartCookie != null) {
			String cartValue = cartCookie.getValue();
			cartUpdateService.updateCartByUuid(tokens, cartValue);
			cartCookie.setPath("/");
			cartCookie.setMaxAge(0);
			response.addCookie(cartCookie);
		}

		return ResponseEntity.ok(tokens);
	}

	@PostMapping("/social-mapping")
	@Operation(summary = "소셜 회원 매핑", description = "가입된 유저의 social 테이블 매핑")
	public ResponseEntity<MessageResponse> socialMapping(@RequestBody SocialMappingDto socialMappingDto) {
		memberService.socialMapping(socialMappingDto);
		return ResponseEntity.ok(new MessageResponse("매핑 완료"));
	}
}
