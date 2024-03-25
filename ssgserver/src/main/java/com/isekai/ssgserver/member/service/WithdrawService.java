package com.isekai.ssgserver.member.service;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.WithdrawInfoDto;
import com.isekai.ssgserver.member.dto.WithdrawMemberDto;
import com.isekai.ssgserver.member.entity.Member;
import com.isekai.ssgserver.member.entity.WithdrawInfo;
import com.isekai.ssgserver.member.repository.MemberRepository;
import com.isekai.ssgserver.member.repository.WithdrawRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WithdrawService {
	private final WithdrawRepository withdrawRepository;
	private final MemberRepository memberRepository;

	public void deleteById(Long id) {
		// 회원이 존재하는지 확인
		Optional<Member> memberOptional = memberRepository.findById(id);
		if (memberOptional.isPresent()) {
			// 회원이 존재하는 경우
			memberRepository.deleteById(id);
		} else {
			// 회원이 존재하지 않는 경우
			throw new CustomException(ErrorCode.NOT_FOUND_USER);
		}
	}

	public void withdrawUpdate(WithdrawMemberDto withdrawMemberDto) {
		/*
		 *  2. 탈퇴여부 0 -> 1로 수정, 탈퇴일시, 수정일자 업데이트
		 *  - is_withdraw, withdraw_at, updated_at
		 */
		try {
			memberRepository.save(Member.toWithdrawMember(withdrawMemberDto));
		} catch (DataAccessException ex) {
			// 데이터베이스 관련 예외 발생 시
			// 로깅을 통해 문제 기록
			log.error("Error occurred while updating withdraw status: {}", ex.getMessage());
			// 사용자에게 적절한 에러 메시지 전송
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			// 그 외의 예외 발생 시
			// 로깅을 통해 문제 기록
			log.error("Unexpected error occurred while updating withdraw status: {}", ex.getMessage());
			// 사용자에게 적절한 에러 메시지 전송
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}

	}

	public void save(WithdrawInfoDto withdrawInfoDto) {
		// 탈퇴이유정보 저장
		try {
			WithdrawInfo withdrawInfo = WithdrawInfo.toWithdrawInfoEntity(withdrawInfoDto);
			withdrawRepository.save(withdrawInfo);
		} catch (DataAccessException e) {
			// 데이터베이스 관련 예외 발생 시
			// 로깅을 통해 문제 기록
			log.error("Error occurred while saving withdraw info: {}", e.getMessage());
			// 사용자에게 적절한 에러 메시지 전송
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// 그 외의 예외 발생 시
			// 로깅을 통해 문제 기록
			log.error("Unexpected error occurred while saving withdraw info: {}", e.getMessage());
			// 사용자에게 적절한 에러 메시지 전송
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	public WithdrawMemberDto findById(Long id) {
		System.out.println("WithdrawService.findById");
		System.out.println("id = " + id);
		Optional<Member> optionalWithdrawMember = memberRepository.findById(id);

		if (optionalWithdrawMember.isPresent()) {
			// 회원 정보가 존재하는 경우
			Member withdrawMember = optionalWithdrawMember.get();
			return WithdrawMemberDto.toWithdrawMemberDto(withdrawMember);
		} else {
			// 회원 정보가 존재하지 않는 경우
			throw new CustomException(ErrorCode.NOT_FOUND_USER);
		}
	}
}
