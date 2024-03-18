package com.isekai.ssgserver.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisService {
    private final Long refreshPeriod = 86400000L;
    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String uuid, String refreshToken) {
        redisTemplate.opsForValue().set(
                uuid,
                refreshToken,
                refreshPeriod,
                TimeUnit.MICROSECONDS
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
