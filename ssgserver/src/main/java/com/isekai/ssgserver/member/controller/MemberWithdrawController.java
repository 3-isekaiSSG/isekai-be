package com.isekai.ssgserver.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.member.dto.WithdrawInfoDto;
import com.isekai.ssgserver.member.dto.WithdrawMemberDto;
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
		try {
			withdrawService.deleteById(id);
			// 회원 삭제가 성공적으로 이루어졌을 경우
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			// 삭제 요청이 성공적으로 처리되었음을 응답으로 반환함. 상태 코드 204(NO_CONTENT)를 응답으로 반환함.
		} catch (CustomException e) {
			// 회원이 존재하지 않는 경우
			log.error("Member not found", e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
			// 상태 코드 404(NOT_FOUND)와 함께 "Member not found" 메시지를 응답으로 반환함.
		}
	}

	@PostMapping("/{id}")
	@Operation(summary = "회원 탈퇴로 변경", description = "해당 하는 회원 상태값을 탈퇴로 변경합니다.")
	public ResponseEntity<Object> withdrawUpdate(@PathVariable Long id) {
		log.info("MemberWithdrawController.withdrawUpdate");
		log.info("id = {}", id);
		try {
			// id로 회원정보 불러오기
			WithdrawMemberDto withdrawMemberDto = withdrawService.findById(id);
			// 상태값 탈퇴로 수정
			withdrawService.withdrawUpdate(withdrawMemberDto);

			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (CustomException exception) {
			// 예외가 발생한 경우
			log.error("Error occurred while updating withdraw status: {}", exception.getMessage());
			// 예외 메시지를 로깅함
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update withdraw status");
			// 상태 코드 500(INTERNAL_SERVER_ERROR)와 함께 적절한 에러 메시지를 응답으로 반환함.
		}
	}

	@PostMapping("/reasons")
	@Operation(summary = "탈퇴 사유 저장", description = "탈퇴 사유 데이터를 저장합니다.")
	public ResponseEntity<Object> withdrawInfoSave(@RequestBody WithdrawInfoDto withdrawInfoDto) {
		log.info("MemberWithdrawController.withdrawInfoSave");
		log.info("withdrawInfo = {}", withdrawInfoDto);

		try {
			withdrawService.save(withdrawInfoDto);
			// 저장 요청이 성공적으로 처리된 경우
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			// 저장 요청이 성공적으로 처리되었음을 응답으로 반환함. 상태 코드 204(NO_CONTENT)를 응답으로 반환함.
		} catch (CustomException exception) {
			// 예외가 발생한 경우
			log.error("Error occurred while saving withdraw info: {}", exception.getMessage());
			// 예외 메시지를 로깅함
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save withdraw info");
			// 상태 코드 500(INTERNAL_SERVER_ERROR)와 함께 적절한 에러 메시지를 응답으로 반환함.
		}
	}

}
