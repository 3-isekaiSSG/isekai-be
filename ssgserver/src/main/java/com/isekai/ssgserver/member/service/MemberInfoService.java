package com.isekai.ssgserver.member.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.AccoutIdDto;
import com.isekai.ssgserver.member.dto.VerificationDto;
import com.isekai.ssgserver.member.entity.Member;
import com.isekai.ssgserver.member.repository.MemberRepository;
import com.isekai.ssgserver.member.repository.VerificationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberInfoService {
	private final MemberRepository memberRepository;
	private final VerificationService verificationService;
	private final VerificationRepository verificationRepository;

	@Transactional
	public String saveByPassword(String uuid, String newPassword) {
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
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String messageRe = "";
		// 일치 확인
		// if (existPassword.equals(newPassword)) {
		if (encoder.matches(existPassword, newPassword)) {
			//* 일치하는 경우 입력값 같음
			log.info("기존 비밀번호랑 일치함");
			messageRe = "현재 사용 중인 비밀번호와 동일합니다. 다른 비밀번호로 다시 입력해주세요.";
		} else {
			//* 다른 경우 변경
			log.info("비밀번호 변경 분기");

			ModelMapper modelMapper = new ModelMapper();
			Member updatedMember = modelMapper.map(member, Member.class);
			updatedMember.setPassword(newPassword);

			memberRepository.save(updatedMember);
			messageRe = "비밀번호를 변경했습니다.";
		}
		return messageRe;
	}

	public AccoutIdDto findMemberId(VerificationDto.SmsVerificationRequest smsVerificationRequest) {
		/* 아이디 찾기 로직
		 *	1. 인증 번호 일치 여부 확인
		 * 	2. 아이디 해당 회원
		 */

		//* 인증번호 일치 여부 확인
		if (verificationService.isVerify(smsVerificationRequest)) {
			throw new CustomException(ErrorCode.WRONG_NUMBER);
		}
		verificationRepository.removeSmsVerification(smsVerificationRequest.getPhone());

		String phoneNum = smsVerificationRequest.getPhone();

		//* 아이디 해당 회원 조회
		Member phoneMember = memberRepository.findByPhone(phoneNum);

		String memberAccountIdValue = phoneMember.getAccountId();
		AccoutIdDto accoutIdDto = new AccoutIdDto();
		accoutIdDto.setAccountId(memberAccountIdValue);
		return accoutIdDto;
	}
}
