package com.isekai.ssgserver.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedisService {
	@Value("${jwt.token.refresh-expire-time}")
	private Long refreshPeriod;
	private final RedisTemplate<String, String> redisTemplate;

	public void saveRefreshToken(String uuid, String refreshToken) {
		redisTemplate.opsForValue().set(
			uuid,
			refreshToken,
			refreshPeriod,
			TimeUnit.MILLISECONDS
		);
	}

	public String getRefreshToken(String uuid) {
		return redisTemplate.opsForValue().get(uuid);
	}

	//삭제
	public void deleteRefreshToken(String uuid) {
		redisTemplate.delete(uuid);
	}
}
