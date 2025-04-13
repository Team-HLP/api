package com.hlp.api.domain.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlp.api.domain.game.dto.request.GameCreateRequest;
import com.hlp.api.domain.game.dto.response.GameResponse;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.repository.GameRepository;
import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Transactional
    public GameResponse createGame(GameCreateRequest request, Integer userId) {
        User user = userRepository.getById(userId);
        Game game = request.toEntity(user);
        return GameResponse.of(gameRepository.save(game));
    }
}
