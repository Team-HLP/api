package com.hlp.api.admin.game.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.game.model.Game;

public interface AdminGameRepository extends Repository<Game, Integer> {
    List<Game> getByUserId(Integer userId);
}
