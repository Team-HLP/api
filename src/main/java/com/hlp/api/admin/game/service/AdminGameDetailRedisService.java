package com.hlp.api.admin.game.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hlp.api.admin.game.dto.response.AdminGameDetailResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminGameDetailRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "admin_game_detail:";

    public void save(String gameId, AdminGameDetailResponse response) {
        String key = KEY_PREFIX + gameId;
        redisTemplate.opsForValue().set(key, response);
    }

    public AdminGameDetailResponse get(String gameId) {
        String key = KEY_PREFIX + gameId;
        Object value = redisTemplate.opsForValue().get(key);
        if (value instanceof AdminGameDetailResponse response) {
            return response;
        }
        return null;
    }

    public void delete(String gameId) {
        redisTemplate.delete(KEY_PREFIX + gameId);
    }
}
