package com.isekai.ssgserver.member.dto;

import java.time.LocalDateTime;

import com.isekai.ssgserver.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WithdrawMemberDto {
	private Long memberId;
	private String uuid;
	private String accountId;
	private String name;
	private String password;
	private String email;
	private String phone;
	private String address;
	private byte gender;
	private byte isWithdraw;
	private LocalDateTime withdrawAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static WithdrawMemberDto toWithdrawMemberDto(Member member) {
		WithdrawMemberDto withdrawMemberDto = new WithdrawMemberDto();
		withdrawMemberDto.setMemberId(member.getMemberId());
		withdrawMemberDto.setUuid(member.getUuid());
		withdrawMemberDto.setAccountId(member.getAccountId());
		withdrawMemberDto.setName(member.getName());
		withdrawMemberDto.setPassword(member.getPassword());
		withdrawMemberDto.setEmail(member.getEmail());
		withdrawMemberDto.setPhone(member.getPhone());
		withdrawMemberDto.setAddress(member.getAddress());
		withdrawMemberDto.setGender(member.getGender());
		withdrawMemberDto.setIsWithdraw(member.getIsWithdraw());
		withdrawMemberDto.setWithdrawAt(member.getWithdrawAt());
		withdrawMemberDto.setCreatedAt(member.getCreditedAt());
		withdrawMemberDto.setUpdatedAt(member.getUpdateAt());
		return withdrawMemberDto;
	}

}
