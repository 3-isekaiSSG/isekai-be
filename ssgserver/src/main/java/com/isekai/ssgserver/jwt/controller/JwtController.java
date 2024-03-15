package com.isekai.ssgserver.jwt.controller;

import com.isekai.ssgserver.jwt.dto.JwtToken;
import com.isekai.ssgserver.jwt.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<?> createToken() {
        JwtToken createdToken = jwtProvider.createToken("sseedd");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", createdToken.getAccessToken());

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
