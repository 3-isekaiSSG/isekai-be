package com.isekai.ssgserver.member.repository;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VerificationRepository {

	private final String PREFIX = "sms:";
	private final int LIMIT_TIME = 3 * 60;

	private final StringRedisTemplate stringRedisTemplate;

	public void createSmsVerification(String phone, String verificationNumber) {
		stringRedisTemplate.opsForValue()
			.set(PREFIX + phone, verificationNumber, Duration.ofSeconds(LIMIT_TIME));
	}

	public String getSmsVerification(String phone) {
		return stringRedisTemplate.opsForValue().get(PREFIX + phone);
	}

	public void removeSmsVerification(String phone) {
		stringRedisTemplate.delete(PREFIX + phone);
	}

	public boolean hasKey(String phone) {
		return stringRedisTemplate.hasKey(PREFIX + phone);
	}
}
