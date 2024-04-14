package com.isekai.ssgserver.member.dto;

import com.isekai.ssgserver.member.entity.MemberSocial;

import lombok.Getter;

@Getter
public class SocialMappingDto {
	private String phone;
	private String memberSocialCode;
	private String provider;

	public MemberSocial toEntity(String uuid, byte socialDivisionCode) {
		return MemberSocial.builder()
			.uuid(uuid)
			.memberSocialCode(this.memberSocialCode)
			.socialDivisionCode(socialDivisionCode)
			.build();
	}
}
