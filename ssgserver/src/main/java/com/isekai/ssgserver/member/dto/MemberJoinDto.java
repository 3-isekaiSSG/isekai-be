package com.isekai.ssgserver.member.dto;

import java.util.UUID;

import com.isekai.ssgserver.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberJoinDto {
	private String accountId;
	private String name;
	private String password;
	private String email;
	private String phone;
	private String zipcode;
	private String address;
	private Integer gender;

	public Member toEntity(String encodedPassword) {
		return Member.builder()
			.uuid(String.valueOf(UUID.randomUUID()))
			.accountId(this.accountId)
			.name(this.name)
			.password(encodedPassword)
			.email(this.email)
			.phone(this.phone)
			.zipcode(this.zipcode == null ? "" : this.zipcode)
			.address(this.address == null ? "" : this.address)
			.gender(this.gender)
			.isWithdraw((byte)0)
			.build();
	}
}