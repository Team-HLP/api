package com.hlp.api.domain.game.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hlp.api.common.auth.user.UserAuth;
import com.hlp.api.domain.game.dto.request.GameCreateRequest;
import com.hlp.api.domain.game.service.GameService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/game")
    public ResponseEntity<Void> createGame(
        @RequestBody @Valid GameCreateRequest request,
        @UserAuth Integer userId
    ) {
        gameService.createGame(request, userId);
        return ResponseEntity.ok().build();
    }
}
