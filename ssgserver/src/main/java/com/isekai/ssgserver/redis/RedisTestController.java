package com.isekai.ssgserver.redis;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "RedisTestController")
public class RedisTestController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/redisTest")
    @Operation(summary = "Redis 데이터 저장 테스트", description = "저장이 제대로 이루어지는지 확인합니다. (저장되는 값은 'banana-yellow', 'apple-red'로 고정)")
    public ResponseEntity<?> addRedisKey() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("banana", "yellow");
        vop.set("apple", "red");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/redisTest/{key}")
    @Operation(summary = "Redis key-value 조회", description = "입력한 key값으로 저장된 value를 조회합니다.")
    public ResponseEntity<?> getReisKey(@PathVariable String key) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(key);
        return ResponseEntity.ok(value);
    }

}
