package com.isekai.ssgserver.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class AccoutIdDto {
	private String accoutId;

	public void setAccountId(String memberAccountIdValue) {
		this.accoutId = memberAccountIdValue;
	}
}
