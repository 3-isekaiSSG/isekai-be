package com.isekai.ssgserver.member.controller;

import com.isekai.ssgserver.member.dto.MemberJoinDto;
import com.isekai.ssgserver.member.dto.MemberLoginDto;
import com.isekai.ssgserver.member.service.MemberService;
import com.isekai.ssgserver.util.MessageResponse;
import com.isekai.ssgserver.util.jwt.JwtToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Member", description = "회원가입, 로그인 등등 기본적으로 멤버 관련 필요한 메소드")
public class MemberController {

    private final MemberService memberService;
    @Value("${jwt.token.refresh-expire-time}")
    private long refreshExpireTime;

    @PostMapping("/join")
    @Operation(summary = "회원가입")
    public ResponseEntity<MessageResponse> join(@Valid @RequestBody MemberJoinDto joinDto) {
        memberService.join(joinDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("회원가입에 성공하셨습니다."));
    }

    @GetMapping("/duplication-id")
    @Operation(summary = "아이디 중복체크", description = "아이디가 중복되었다면 409 error, 아니라면 200 ok status와 메시지 반환")
    public ResponseEntity<MessageResponse> duplicationCheck(@RequestParam String inputId) {
        memberService.isDuplicate(inputId);
        return ResponseEntity.ok(new MessageResponse("사용 가능한 아이디입니다."));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<JwtToken> login(@Valid @RequestBody MemberLoginDto loginDto) {
        JwtToken tokens = memberService.login(loginDto);
        // access token -> header - Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokens.getAccessToken());

        // refresh token -> header -> set Cookie
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokens.getRefreshToken())
            .httpOnly(true)
            .secure(true) // HTTPS 환경에서만 사용할 경우 true로 설정
            .sameSite("Lax")  // 같은 사이트 내의 요청에서만 쿠키를 전송
            .path("/")
            .maxAge(refreshExpireTime) // 쿠키의 유효 기간 설정
            .build();
        headers.set(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok()
            .headers(headers)
            .build();
    }
}
