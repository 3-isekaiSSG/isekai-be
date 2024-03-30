package com.isekai.ssgserver.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isekai.ssgserver.member.dto.WithdrawInfoDto;
import com.isekai.ssgserver.member.service.WithdrawService;

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

	@DeleteMapping("/delete/{id}")
	@Operation(summary = "회원 테이블에 데이터 제거", description = "id pk 값으로 회원 테이블에서 해당 하는 데이터를 삭제합니다.")
	public ResponseEntity<Object> deleteById(@PathVariable Long id) {
		log.info("MemberWithdrawController.deleteById");
		log.info("id = {}", id);
		withdrawService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{uuid}")
	@Operation(summary = "회원 탈퇴로 변경", description = "해당 하는 회원 상태값을 탈퇴로 변경합니다.")
	public ResponseEntity<Object> withdrawUpdate(@PathVariable String uuid) {
		log.info("MemberWithdrawController.withdrawUpdate");
		log.info("uuid = {}", uuid);
		withdrawService.withdrawUpdate(uuid);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/reasons")
	@Operation(summary = "탈퇴 사유 저장", description = "탈퇴 사유 데이터를 저장합니다.")
	public ResponseEntity<Object> withdrawInfoSave(@RequestBody WithdrawInfoDto withdrawInfoDto) {
		log.info("MemberWithdrawController.withdrawInfoSave");
		log.info("withdrawInfo = {}", withdrawInfoDto);
		withdrawService.reasonsSave(withdrawInfoDto);
		return ResponseEntity.ok().build();
	}
}
