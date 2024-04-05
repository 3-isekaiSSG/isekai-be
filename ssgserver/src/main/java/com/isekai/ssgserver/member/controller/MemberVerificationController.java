package com.isekai.ssgserver.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.member.dto.VerificationDto;
import com.isekai.ssgserver.member.service.VerificationService;
import com.isekai.ssgserver.util.MessageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/phone-verification")
@Tag(name = "Phone Verification")
@Slf4j
public class MemberVerificationController {

	private final VerificationService verificationService;

	@PostMapping("/send")
	@Operation(summary = "휴대폰인증 문자 발송", description = "회원가입 휴대폰 인증 번호를 발송할 수 있습니다. 이미 회원인 경우 문자 발송이 되지 않습니다.")
	public ResponseEntity<MessageResponse> sendSms(
		@RequestBody VerificationDto.SmsVerificationRequest smsVerificationRequest) {
		log.info("controller 접근");
		verificationService.sendSms(smsVerificationRequest);
		return new ResponseEntity<>(new MessageResponse("인증 번호가 발송되었습니다."),
			HttpStatus.OK);
	}

	@PostMapping("/confirm")
	@Operation(summary = "인증번호 일치 확인", description = "인증번호가 일치하는지 확인합니다. 3분이 지나면 인증번호가 유효하지 않습니다.")
	public ResponseEntity<MessageResponse> SmsVerification(
		@RequestBody VerificationDto.SmsVerificationRequest smsVerificationRequest) {
		verificationService.verifySms(smsVerificationRequest);
		return new ResponseEntity<>(new MessageResponse("휴대폰 번호 인증에 성공하였습니다."),
			HttpStatus.OK);
	}
}
