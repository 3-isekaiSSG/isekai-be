package com.isekai.ssgserver.util.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {

	@Value("${jwt.token.refresh-expire-time}")
	private long refreshExpireTime;

	private final JwtProvider jwtProvider;

	/**
	 * token 생성 및 header 전송 테스트 !
	 * (지울 예정)
	 */
	@GetMapping("/token/test")
	@Operation(summary = "JWT 토큰 생성 Test", description = "Access Token은 Header로 전송, Refresh Token은 Cookie로 설정됩니다.")
	public ResponseEntity<Void> createToken(HttpServletResponse response) {
		JwtToken createdTokenInfo = jwtProvider.createToken("sseedd");
		// access token -> header - Authorization
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", createdTokenInfo.getAccessToken());

		// refresh token -> header -> set Cookie
		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", createdTokenInfo.getRefreshToken())
			.httpOnly(true)
			.secure(true) // HTTPS 환경에서만 사용할 경우 true로 설정
			.sameSite("Lax")  // 같은 사이트 내의 요청에서만 쿠키를 전송
			.path("/")
			.maxAge(refreshExpireTime) // 쿠키의 유효 기간 설정
			.build();
		response.setHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

		return ResponseEntity.ok()
			.headers(headers)
			.build();
	}

	/**
	 * JWT Access Token 재발급
	 * - Security Filter를 거치지 않음
	 */
	@GetMapping("/token")
	@Operation(summary = "JWT 토큰 재발급", description = "Access token을 재발급하며, Refresh token도 갱신됩니다.")
	public ResponseEntity<Void> reissueToken(@CookieValue(name = "refreshToken") String refreshToken,
		HttpServletResponse response) {
		System.out.println("refresh token 재발급 controller 작동");
		HttpHeaders headers = new HttpHeaders();
		JwtToken createdTokenInfo = jwtProvider.reissueToken(refreshToken);
		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", createdTokenInfo.getRefreshToken())
			.httpOnly(true)
			.secure(true) // HTTPS 환경에서만 사용할 경우 true로 설정
			.sameSite("Lax")  // 같은 사이트 내의 요청에서만 쿠키를 전송
			.path("/")
			.maxAge(refreshExpireTime) // 쿠키의 유효 기간 설정
			.build();
		response.setHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
		response.setHeader(HttpHeaders.AUTHORIZATION, createdTokenInfo.getAccessToken());
		return ResponseEntity.ok().build();
	}
}
