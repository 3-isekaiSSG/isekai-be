package com.isekai.ssgserver.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WithdrawInfoDto {
	private Long withdrawInfoId;
	private String uuid;
	private String reason;

}
