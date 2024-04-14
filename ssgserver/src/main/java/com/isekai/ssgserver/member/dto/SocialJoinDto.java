package com.isekai.ssgserver.member.dto;

import com.isekai.ssgserver.member.entity.MemberSocial;

import lombok.Getter;

@Getter
public class SocialJoinDto {
	private String uuid;
	private String memberSocialCode;
	private String socialDivisionCode;

	public static MemberSocial joinToEntity(String uuid, String memberSocialCode, byte socialDivisionCode) {
		return MemberSocial.builder()
			.uuid(uuid)
			.memberSocialCode(memberSocialCode)
			.socialDivisionCode(socialDivisionCode)
			.build();
	}
}
