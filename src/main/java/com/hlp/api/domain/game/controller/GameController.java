package com.hlp.api.domain.game.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hlp.api.common.auth.user.UserAuth;
import com.hlp.api.domain.game.dto.request.GameCreateRequest;
import com.hlp.api.domain.game.dto.response.GameResponse;
import com.hlp.api.domain.game.service.GameService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameController implements GameApi{

    private final GameService gameService;

    @PostMapping("/game")
    public ResponseEntity<GameResponse> createGame(
        @RequestBody @Valid GameCreateRequest request,
        @UserAuth Integer userId
    ) {
        GameResponse response = gameService.createGame(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameResponse>> getGames(
        @UserAuth Integer userId
    ) {
        List<GameResponse> response = gameService.getGames(userId);
        return ResponseEntity.ok(response);
    }
}
