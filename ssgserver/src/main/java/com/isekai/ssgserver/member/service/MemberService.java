package com.isekai.ssgserver.member.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.MemberJoinDto;
import com.isekai.ssgserver.member.dto.MemberLoginDto;
import com.isekai.ssgserver.member.dto.SocialMemberDto;
import com.isekai.ssgserver.member.entity.Member;
import com.isekai.ssgserver.member.entity.MemberSocial;
import com.isekai.ssgserver.member.repository.MemberRepository;
import com.isekai.ssgserver.member.repository.SocialRepository;
import com.isekai.ssgserver.util.jwt.JwtProvider;
import com.isekai.ssgserver.util.jwt.JwtToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;
	private final SocialRepository socialRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	public void isDuplicate(String accountId) {
		Optional<Member> existingMember = memberRepository.findByAccountId(accountId);
		if (existingMember.isPresent()) {
			throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
		}
	}

	public void join(MemberJoinDto joinDto) {
		// 가입 정보 저장 + 비밀번호는 암호화해서 저장
		String encodedPassword = passwordEncoder.encode(joinDto.getPassword());

		Member member = joinDto.toEntity(encodedPassword);
		memberRepository.save(member);
	}

	public JwtToken login(MemberLoginDto loginDto) {
		Member member = memberRepository.findByAccountId(loginDto.getAccountId())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_ERROR);
		}

		return jwtProvider.createToken(member.getUuid());
	}

	public JwtToken socialLogin(SocialMemberDto isMemberDto) {
		MemberSocial social = socialRepository.findByMemberSocialCode(isMemberDto.getSocialCode())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		Member member = memberRepository.findByUuid(social.getUuid())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		return jwtProvider.createToken(member.getUuid());
	}

	/**
	 * uuid로 member_id (pk) 조회 + 활동 회원인지 검증
	 */
	public Long getMemberIdByUuid(String uuid) {
		return memberRepository.findByUuidAndIsWithdraw(uuid, (byte)0)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))
				.getMemberId();
	}
}
