package com.example.wooriga;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisRepositoryImpl implements RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(RedisInfo redisInfo) {
        redisTemplate.opsForValue().set(redisInfo.getKey(), String.valueOf(redisInfo.getValue()));
    }

}
