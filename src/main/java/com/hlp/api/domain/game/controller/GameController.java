package com.hlp.api.domain.game.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hlp.api.common.auth.user.UserAuth;
import com.hlp.api.domain.game.dto.request.MeteoriteCreateRequest;
import com.hlp.api.domain.game.dto.request.MoleCreateRequest;
import com.hlp.api.domain.game.dto.response.GameExistResponse;
import com.hlp.api.domain.game.model.GameCategory;
import com.hlp.api.domain.game.service.GameService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameController implements GameApi{

    private final GameService gameService;

    @PostMapping("/game/meteorite")
    public ResponseEntity<Void> crateMeteorite(
        // @RequestBody @Valid MeteoriteCreateRequest request,
        @RequestPart("request") @Valid MeteoriteCreateRequest request,
        @RequestPart("eeg_data_file") MultipartFile eegDataFile,
        @RequestPart("eye_data_file") MultipartFile eyeDataFile,
        @RequestPart("behavior_file") MultipartFile behaviorDataFile,
        @UserAuth Integer userId
    ) {
        gameService.crateMeteorite(request, eegDataFile, eyeDataFile, behaviorDataFile, userId);
        // gameService.crateMeteorite(request, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/game/mole")
    public ResponseEntity<Void> createMole(
        // @RequestBody @Valid MoleCreateRequest request,
        @RequestPart("request") @Valid MoleCreateRequest request,
        @RequestPart("eeg_data_file") MultipartFile eegDataFile,
        @RequestPart("eye_data_file") MultipartFile eyeDataFile,
        @RequestPart("behavior_file") MultipartFile behaviorDataFile,
        @UserAuth Integer userId
    ) {
        gameService.crateMole(request, eegDataFile, eyeDataFile, behaviorDataFile, userId);
        // gameService.crateMole(request, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/games")
    public ResponseEntity<List<Object>> getGames(
        @UserAuth Integer userId,
        @RequestParam GameCategory gameCategory
    ) {
        List<Object> response = gameService.getGames(userId, gameCategory);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/games/exist")
    public ResponseEntity<GameExistResponse> getGamesExist(
        @UserAuth Integer userId,
        @RequestParam GameCategory gameCategory
    ) {
        GameExistResponse response = gameService.getGamesExist(userId, gameCategory);
        return ResponseEntity.ok(response);
    }
}
