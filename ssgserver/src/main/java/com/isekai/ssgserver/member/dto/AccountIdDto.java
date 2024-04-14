package com.isekai.ssgserver.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class AccountIdDto {
	private String accountId;

	public void setAccountId(String memberAccountIdValue) {
		this.accountId = memberAccountIdValue;
	}
}
