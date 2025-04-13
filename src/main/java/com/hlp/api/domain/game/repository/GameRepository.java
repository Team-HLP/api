package com.hlp.api.domain.game.repository;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.game.model.Game;

public interface GameRepository extends Repository<Game, Integer> {
    Game save(Game game);
}
