package com.isekai.ssgserver.member.dto;

import lombok.Getter;

public class VerificationDto {

	@Getter
	public static class SmsVerificationRequest {
		private String phone;
		private String verificationNumber;
	}
}
