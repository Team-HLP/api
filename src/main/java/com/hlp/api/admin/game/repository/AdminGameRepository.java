package com.hlp.api.admin.game.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.game.exception.GameNotFoundException;
import com.hlp.api.domain.game.model.Game;

public interface AdminGameRepository extends Repository<Game, Integer> {
    List<Game> findAllByUserId(Integer userId);

    Optional<Game> findByIdAndUserId(Integer gameId, Integer userId);

    default Game getByIdAndUserId(Integer gameId, Integer userId) {
        return findByIdAndUserId(gameId, userId)
            .orElseThrow(() -> new GameNotFoundException("game id " + gameId + " user id" + userId));
    }
}
