package com.isekai.ssgserver.member.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.AccountIdDto;
import com.isekai.ssgserver.member.dto.InfoPasswordDto;
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
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public String updateByPassword(String uuid, InfoPasswordDto infoPasswordDto) {

		String newPassword = infoPasswordDto.getNewPassword();
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		String existPassword = member.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String messageRe = "";

		if (encoder.matches(newPassword, existPassword)) {
			messageRe = "현재 사용 중인 비밀번호와 동일합니다. 다른 비밀번호로 다시 입력해주세요.";
		} else {
			
			ModelMapper modelMapper = new ModelMapper();
			Member updatedMember = modelMapper.map(member, Member.class);

			String encodedPassword = passwordEncoder.encode(newPassword);
			updatedMember.setPassword(encodedPassword);

			memberRepository.save(updatedMember);
			messageRe = "비밀번호를 변경했습니다.";
		}
		return messageRe;
	}

	@Transactional
	public AccountIdDto getMemberId(VerificationDto.SmsVerificationRequest smsVerificationRequest) {
		if (verificationService.isVerify(smsVerificationRequest)) {
			throw new CustomException(ErrorCode.WRONG_NUMBER);
		} else {
			verificationRepository.removeSmsVerification(smsVerificationRequest.getPhone());

			String phoneNum = smsVerificationRequest.getPhone();

			Member phoneMember = memberRepository.findByPhone(phoneNum)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

			String memberAccountIdValue = phoneMember.getAccountId();
			AccountIdDto accountIdDto = new AccountIdDto();
			accountIdDto.setAccountId(memberAccountIdValue);
			return accountIdDto;
		}
	}
}
