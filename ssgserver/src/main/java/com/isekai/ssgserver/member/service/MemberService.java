package com.isekai.ssgserver.member.service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.isekai.ssgserver.member.dto.SocialMappingDto;
import org.apache.catalina.util.CustomObjectInputStream;
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

	public String join(MemberJoinDto joinDto) {
		String uuid = String.valueOf(UUID.randomUUID());
		String encodedPassword = passwordEncoder.encode(joinDto.getPassword());

		// 가입 정보 저장 + 비밀번호는 암호화해서 저장
		Member member = joinDto.toEntity(uuid, encodedPassword);
		memberRepository.save(member);

		// 소셜 로그인을 했다면 uuid를 반환하도록
		if (Objects.equals(joinDto.getPassword(), "kakao")) {
			return uuid;
		}

		return null;
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

	public void socialJoin(SocialJoinDto socialJoinDto) {
		Member member = memberRepository.findByUuid(socialJoinDto.getUuid())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		byte code = (byte)0;
		if (Objects.equals(socialJoinDto.getSocialDivisionCode(), "kakao")) {
			code = (byte)1;
		}

		MemberSocial memberSocial = socialJoinDto.joinToEntity(code);
		socialRepository.save(memberSocial);
	}

	public void socialMapping(SocialMappingDto socialMappingDto) {
		Member member = memberRepository.findByPhone(socialMappingDto.getPhone())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		String uuid = member.getUuid();

		byte code = (byte)0;
		if (Objects.equals(socialMappingDto.getProvider(), "kakao")) {
			code = (byte)1;
		}

		MemberSocial memberSocial = new SocialJoinDto().toEntity(uuid, code);
		socialRepository.save(memberSocial);

	/**
	 * uuid로 member_id (pk) 조회 + 활동 회원인지 검증
	 */
	public Long getMemberIdByUuid(String uuid) {
		return memberRepository.findByUuidAndIsWithdraw(uuid, (byte)0)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))
				.getMemberId();
	}
}
