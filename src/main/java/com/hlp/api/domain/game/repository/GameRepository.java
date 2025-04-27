package com.hlp.api.domain.game.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.model.GameCategory;

public interface GameRepository extends Repository<Game, Integer> {
    Game save(Game game);

    List<Game> findAllByUserId(Integer userId);

    List<Game> findAllByUserIdAndGameCategory(Integer userId, GameCategory gameCategory);
}
