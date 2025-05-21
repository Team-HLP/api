package com.hlp.api.admin.game.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hlp.api.domain.guardian.dto.response.ChildADHDStatisticsResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChildAdhdStatisticsRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "adhd_stats:";

    public void save(String gameId, ChildADHDStatisticsResponse response) {
        String key = KEY_PREFIX + gameId;
        redisTemplate.opsForValue().set(key, response);
    }

    public ChildADHDStatisticsResponse get(String gameId) {
        String key = KEY_PREFIX + gameId;
        Object value = redisTemplate.opsForValue().get(key);
        if (value instanceof ChildADHDStatisticsResponse response) {
            return response;
        }
        return null;
    }

    public void delete(String childId) {
        redisTemplate.delete(KEY_PREFIX + childId);
    }
}
