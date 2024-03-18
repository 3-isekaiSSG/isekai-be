package com.isekai.ssgserver.jwt.controller;

import com.isekai.ssgserver.jwt.dto.JwtToken;
import com.isekai.ssgserver.jwt.service.JwtProvider;
import io.jsonwebtoken.JwtBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtProvider jwtProvider;

    /**
     * token 생성 및 header 전송 테스트 !
     * (지울 예정)
     */
    @GetMapping("/token/test")
    public ResponseEntity<?> createToken(HttpServletResponse response) {
        JwtToken createdToken = jwtProvider.createToken("sseedd");
        // access token -> header - Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", createdToken.getAccessToken());

        // refresh token -> header -> set Cookie
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", createdToken.getRefreshToken())
                .httpOnly(true)
                .secure(true) // HTTPS 환경에서만 사용할 경우 true로 설정
                .path("/")
                .maxAge(86400000) // 쿠키의 유효 기간을 설정 (예: 7일)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @GetMapping("/token/{uuid}")
    public ResponseEntity<?> reissueToken(@RequestHeader("Authorization") String token, @PathVariable String uuid) {
        System.out.println("controller 작동");
        HttpHeaders headers = new HttpHeaders();
//        JwtToken createdToken = jwtProvider.reissueToken(token, uuid);
        jwtProvider.reissueToken(token, uuid);
//        headers.set("Authorization", createdToken.getAccessToken());
        return ResponseEntity.ok().build();
    }
}
