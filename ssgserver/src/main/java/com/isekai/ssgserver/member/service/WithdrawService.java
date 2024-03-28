package com.isekai.ssgserver.member.service;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.WithdrawInfoDto;
import com.isekai.ssgserver.member.entity.Member;
import com.isekai.ssgserver.member.entity.WithdrawInfo;
import com.isekai.ssgserver.member.repository.MemberRepository;
import com.isekai.ssgserver.member.repository.WithdrawRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WithdrawService {
	private final WithdrawRepository withdrawRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void deleteById(Long id) {
		// 회원이 존재하는지 확인
		// 해당 하는 부분 orElseThrow : else인 경우만 던짐
		Member memberOptional = memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		memberRepository.deleteById(id);
	}

	@Transactional
	public void withdrawUpdate(String uuid) {
		/*
		 * 1. 회원 찾기
		 * 2. 탈퇴여부 0 -> 1로 수정, 탈퇴일시, 수정일자 업데이트
		 *  - is_withdraw, withdraw_at, updated_at
		 */
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		Member withdrawMember = Member.builder()
			.memberId(member.getMemberId())
			.uuid(member.getUuid())
			.accountId(member.getAccountId())
			.name((member.getName()))
			.password(member.getPassword())
			.email(member.getEmail())
			.phone(member.getPhone())
			.address(member.getAddress())
			.gender(member.getGender())
			.isWithdraw((byte)1)
			.build();

		memberRepository.save(withdrawMember);
	}

	@Transactional
	public void reasonsSave(WithdrawInfoDto withdrawInfoDto) {
		WithdrawInfo withdrawInfo = WithdrawInfo.toWithdrawInfoEntity(withdrawInfoDto);
		withdrawRepository.save(withdrawInfo);
	}
}
