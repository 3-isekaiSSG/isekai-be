package com.isekai.ssgserver.member.dto;

import com.isekai.ssgserver.member.entity.MemberSocial;

import lombok.Getter;

@Getter
public class SocialJoinDto {
	private String uuid;
	private String memberSocialCode;
	private byte socialDivisionCode;

	public MemberSocial joinToEntity() {
		return MemberSocial.builder()
			.uuid(this.uuid)
			.memberSocialCode(this.memberSocialCode)
			.socialDivisionCode(this.socialDivisionCode)
			.build();
	}

	public MemberSocial toEntity(String uuid) {
		return MemberSocial.builder()
			.uuid(uuid)
			.memberSocialCode(this.memberSocialCode)
			.socialDivisionCode(this.socialDivisionCode)
			.build();
	}
}
