package com.isekai.ssgserver.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class WithdrawInfoDto {
	private Long withdrawInfoId;
	private String uuid;
	private String reason;
}