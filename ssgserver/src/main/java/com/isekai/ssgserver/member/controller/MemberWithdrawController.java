package com.isekai.ssgserver.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isekai.ssgserver.member.dto.WithdrawInfoDto;
import com.isekai.ssgserver.member.service.WithdrawService;
import com.isekai.ssgserver.util.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/withdraw")
@Slf4j
@Tag(name = "Withdraw", description = "회원 탈퇴 API document")
public class MemberWithdrawController {
	private final WithdrawService withdrawService;
	private final JwtProvider jwtProvider;

	@PutMapping("")
	@Operation(summary = "회원 탈퇴로 변경", description = "해당 하는 회원 상태값을 탈퇴로 변경합니다.")
	public ResponseEntity<Void> updateWithdraw(
		@RequestHeader("Authorization") String token) {
		String uuid = jwtProvider.getUuid(token);
		withdrawService.updateWithdraw(uuid);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/reasons")
	@Operation(summary = "탈퇴 사유 저장", description = "탈퇴 사유 데이터를 저장합니다.")
	public ResponseEntity<Void> addWithdrawInfo(
		@RequestHeader("Authorization") String token,
		@RequestBody WithdrawInfoDto withdrawInfoDto) {
		String uuid = jwtProvider.getUuid(token);
		withdrawService.addWithdrawReasons(uuid, withdrawInfoDto);
		return ResponseEntity.ok().build();
	}
}
