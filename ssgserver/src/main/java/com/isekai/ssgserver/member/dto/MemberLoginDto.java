package com.isekai.ssgserver.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberLoginDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String accountId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
