package com.isekai.ssgserver.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isekai.ssgserver.member.dto.AccoutIdDto;
import com.isekai.ssgserver.member.dto.InfoPasswordDto;
import com.isekai.ssgserver.member.dto.VerificationDto;
import com.isekai.ssgserver.member.service.MemberInfoService;
import com.isekai.ssgserver.util.MessageResponse;

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

	@PutMapping("/password")
	@Operation(summary = "회원 비밀번호 재설정", description = "회원 비밀번호 입력한 값으로 변경")
	public ResponseEntity<?> updatePassword(
		// @RequestBody Map<String, String> requestBody
		@RequestBody InfoPasswordDto infoPasswordDto) {
		// String uuid = requestBody.get("uuid");
		// String newPassword = requestBody.get("newPassword");
		log.info("MemberInfoController.updatePassword");
		log.info("infoPasswordDto = " + infoPasswordDto);
		String resopseMessage = memberInfoService.saveByPassword(infoPasswordDto);
		return new ResponseEntity<>(new MessageResponse(resopseMessage), HttpStatus.OK);
	}

	@PostMapping("/Id")
	@Operation(summary = "회원 아이디 찾기", description = "인증번호 확인 후 회원 아이디 알려주기")
	public ResponseEntity<?> findMemeberId(
		@RequestBody VerificationDto.SmsVerificationRequest smsVerificationRequest
	) {
		log.info("MemberInfoController.findMemeberId");
		log.info("smsVerificationRequest = " + smsVerificationRequest);
		AccoutIdDto accountId = memberInfoService.findMemberId(smsVerificationRequest);

		return ResponseEntity.ok(accountId);

	}

}
