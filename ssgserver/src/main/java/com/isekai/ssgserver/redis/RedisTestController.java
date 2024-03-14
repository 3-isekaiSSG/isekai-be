package com.isekai.ssgserver.redis;

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
    public ResponseEntity<?> addRedisKey() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("mirim", "jang");
        vop.set("jang", "mirim");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/redisTest/{key}")
    public ResponseEntity<?> getReisKey(@PathVariable String key) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(key);
        return ResponseEntity.ok(value);
    }

}
