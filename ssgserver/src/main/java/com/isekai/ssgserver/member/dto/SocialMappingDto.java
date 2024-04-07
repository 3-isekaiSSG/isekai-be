package com.isekai.ssgserver.member.dto;

import com.isekai.ssgserver.member.entity.MemberSocial;
import lombok.Getter;

@Getter
public class SocialMappingDto {
    private String uuid;
    private String memberSocialCode;
    private byte socialDivisionCode;

    public MemberSocial toEntity(byte socialDivisionCode) {
        return MemberSocial.builder()
            .uuid(this.uuid)
            .memberSocialCode(this.memberSocialCode)
            .socialDivisionCode(socialDivisionCode)
            .build();
    }
}
