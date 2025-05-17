package com.hlp.api.domain.game.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.game.exception.GameNotFoundException;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.model.GameCategory;

public interface GameRepository extends Repository<Game, Integer> {
    Game save(Game game);

    List<Game> findAllByUserId(Integer userId);

    List<Game> findAllByUserIdAndGameCategory(Integer userId, GameCategory gameCategory);

    Optional<Game> findById(Integer gameId);

    default Game getById(Integer gameId) {
        return findById(gameId)
            .orElseThrow(() -> new GameNotFoundException("존재하지 않는 게임 정보입니다."));
    }

    boolean existsByUserIdAndGameCategory(Integer userId, GameCategory gameCategory);
}
