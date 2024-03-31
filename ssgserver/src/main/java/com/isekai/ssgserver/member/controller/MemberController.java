package com.isekai.ssgserver.member.controller;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.MemberJoinDto;
import com.isekai.ssgserver.member.dto.MemberLoginDto;
import com.isekai.ssgserver.member.service.MemberService;
import com.isekai.ssgserver.util.MessageResponse;
import com.isekai.ssgserver.util.jwt.JwtToken;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "회원가입")
    public ResponseEntity<String> join(@Valid @RequestBody MemberJoinDto joinDto) {
        memberService.join(joinDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/duplication-id")
    @Operation(summary = "아이디 중복체크", description = "아이디가 중복되었다면 409 error, 아니라면 200 ok status와 메시지 반환")
    public ResponseEntity<MessageResponse> duplicationCheck(@RequestParam String inputId) {
        try {
            memberService.isDuplicate(inputId);
            return new ResponseEntity<>(new MessageResponse("존재하지 않는 사용자입니다."),
                HttpStatus.OK);
        } catch (CustomException e) {
            if (e.getErrorCode() == ErrorCode.ALREADY_EXIST_USER) {
                return new ResponseEntity<>(new MessageResponse(e.getErrorCode().getMessage()),
                    HttpStatus.CONFLICT);
            }
            throw e;
        }
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<JwtToken> login(@Valid @RequestBody MemberLoginDto loginDto) {
        JwtToken tokens = memberService.login(loginDto);
        return ResponseEntity.ok(tokens);
    }
}
