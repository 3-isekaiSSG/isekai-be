package com.isekai.ssgserver.member.dto;

import com.isekai.ssgserver.member.entity.MemberSocial;

import lombok.Getter;

@Getter
public class SocialJoinDto {
	private String uuid;
	private String memberSocialCode;
	private String socialDivisionCode;

	public MemberSocial joinToEntity(byte socialDivisionCode) {
		return MemberSocial.builder()
			.uuid(this.uuid)
			.memberSocialCode(this.memberSocialCode)
			.socialDivisionCode(socialDivisionCode)
			.build();
	}
}
