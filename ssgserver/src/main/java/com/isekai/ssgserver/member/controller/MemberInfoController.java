package com.isekai.ssgserver.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isekai.ssgserver.member.dto.AccoutIdDto;
import com.isekai.ssgserver.member.dto.InfoPasswordDto;
import com.isekai.ssgserver.member.dto.VerificationDto;
import com.isekai.ssgserver.member.service.MemberInfoService;
import com.isekai.ssgserver.util.MessageResponse;
import com.isekai.ssgserver.util.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/info")
@Slf4j
@Tag(name = "MemberInfo", description = "비밀번호 재설정, 아이디 찾기")
public class MemberInfoController {

	private final MemberInfoService memberInfoService;
	private final JwtProvider jwtProvider;

	@PutMapping("/password")
	@Operation(summary = "회원 비밀번호 재설정", description = "회원 비밀번호 입력한 값으로 변경")
	public ResponseEntity<MessageResponse> updateMemberPassword(
		@RequestHeader("Authorization") String token,
		@RequestBody InfoPasswordDto infoPasswordDto) {

		String uuid = jwtProvider.getUuid(token);
		String resopseMessage = memberInfoService.modifyByPassword(uuid, infoPasswordDto);
		return new ResponseEntity<>(new MessageResponse(resopseMessage), HttpStatus.OK);
	}

	@GetMapping("/id")
	@Operation(summary = "회원 아이디 찾기", description = "인증번호 확인 후 회원 아이디 알려주기")
	public ResponseEntity<AccoutIdDto> addMemberIdDetails(
		@RequestBody VerificationDto.SmsVerificationRequest smsVerificationRequest
	) {
		AccoutIdDto accountIdDto = memberInfoService.findMemberId(smsVerificationRequest);

		return ResponseEntity.ok(accountIdDto);

	}

}
