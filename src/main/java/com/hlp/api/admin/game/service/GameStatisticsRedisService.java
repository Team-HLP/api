package com.hlp.api.admin.game.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hlp.api.admin.game.dto.response.AdminGameStatisticsResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameStatisticsRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "admin_game_stats:";

    public void save(String gameId, AdminGameStatisticsResponse response) {
        String key = KEY_PREFIX + gameId;
        redisTemplate.opsForValue().set(key, response);
    }

    public AdminGameStatisticsResponse get(String gameId) {
        String key = KEY_PREFIX + gameId;
        Object value = redisTemplate.opsForValue().get(key);
        if (value instanceof AdminGameStatisticsResponse) {
            return (AdminGameStatisticsResponse) value;
        }
        return null;
    }

    public void delete(String gameId) {
        String key = KEY_PREFIX + gameId;
        redisTemplate.delete(key);
    }
}
