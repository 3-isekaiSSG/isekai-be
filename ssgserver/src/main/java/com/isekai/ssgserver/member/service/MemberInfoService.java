package com.isekai.ssgserver.member.service;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.entity.Member;
import com.isekai.ssgserver.member.repository.MemberInfoRepository;
import com.isekai.ssgserver.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberInfoService {
	private final MemberInfoRepository memberInfoRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void saveByPassword(String uuid, String newPassword) {
		/* 비밀번호 재설정 로직
		 * 	1. 기존 비밀번호랑 새번호 일치 여부 조회
		 * 	2. 비밀번호 재설정
		 * 		필요데이터 : 기존 비밀번호, 변경 비밀번호
		 */

		//* 기존 password랑 일치하는지 확인
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		// 기존 비밀번호 조회
		String existPassword = member.getPassword();

		// 일치 확인
		if (existPassword == newPassword) {
			// 일치하는 경우 입력값 같음
			System.out.println("기존 비밀번호랑 일치함");

		} else {
			// 다른 경우 변경
			// 하단 modelMapper 사용??
			System.out.println("비밀번호 변경 분기");
			Member updatedMember = Member.builder()
				.memberId(member.getMemberId())
				.uuid(uuid)
				.accountId(member.getAccountId())
				.name(member.getName())
				.password(newPassword)
				.email(member.getEmail())
				.phone(member.getPhone())
				.address(member.getAddress())
				.gender(member.getGender())
				.build();

			memberRepository.save(updatedMember);
		}

	}
}
