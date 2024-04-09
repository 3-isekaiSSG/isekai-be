package com.isekai.ssgserver.member.service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.MemberJoinDto;
import com.isekai.ssgserver.member.dto.MemberLoginDto;
import com.isekai.ssgserver.member.dto.SocialJoinDto;
import com.isekai.ssgserver.member.dto.SocialMappingDto;
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
		String uuid = String.valueOf(UUID.randomUUID());
		String encodedPassword = passwordEncoder.encode(joinDto.getPassword());

		// 가입 정보 저장 + 비밀번호는 암호화해서 저장
		Member member = joinDto.toEntity(uuid, encodedPassword);
		memberRepository.save(member);

		byte code = -1;
		// 소셜 로그인을 했다면 소셜 매핑도 처리해주기. 추가로 네이버 등등 들어오면 1, 2, 3 처리
		if (Objects.equals(joinDto.getPassword(), "kakao")) {
			code = (byte)0;
		}

		if (code == -1) {
			return;
		}

		MemberSocial memberSocial = SocialJoinDto.joinToEntity(uuid, joinDto.getAccountId(), code);
		socialRepository.save(memberSocial);
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

	public void socialMapping(SocialMappingDto socialMappingDto) {
		Member member = memberRepository.findByPhone(socialMappingDto.getPhone())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		String uuid = member.getUuid();

		byte code = -1;
		if (Objects.equals(socialMappingDto.getProvider(), "kakao")) {
			code = (byte)0;
		}

		if (code == -1) {
			return;
		}

		MemberSocial memberSocial = socialMappingDto.toEntity(uuid, code);
		socialRepository.save(memberSocial);
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
