package com.isekai.ssgserver.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class InfoPasswordDto {
	private String uuid;
	private String newPassword;
}
