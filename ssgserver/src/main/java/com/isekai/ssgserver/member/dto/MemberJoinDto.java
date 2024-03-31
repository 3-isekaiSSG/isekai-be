package com.isekai.ssgserver.member.dto;

import com.isekai.ssgserver.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberJoinDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String accountId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리로 입력해주세요.")
    private String password;

    private String email;
    private String phone;
    private String address;
    private byte gender;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
            .accountId(this.accountId)
            .name(this.name)
            .password(encodedPassword)
            .email(this.email)
            .phone(this.phone)
            .address(this.address)
            .gender(this.gender)
            .build();
    }
}
