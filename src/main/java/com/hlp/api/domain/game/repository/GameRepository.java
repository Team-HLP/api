package com.hlp.api.domain.game.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.game.model.Game;

public interface GameRepository extends Repository<Game, Integer> {
    Game save(Game game);

    List<Game> findAllByUserId(Integer userId);
}
