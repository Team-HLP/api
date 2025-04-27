package com.hlp.api.domain.game.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.hlp.api.common.auth.user.UserAuth;
import com.hlp.api.domain.game.dto.request.GameCreateRequest;
import com.hlp.api.domain.game.dto.response.GameResponse;
import com.hlp.api.domain.game.model.GameCategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "(Normal) Game: 게임", description = "게임 API")
public interface GameApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "게임 정보 생성")
    @PostMapping("/game")
    ResponseEntity<GameResponse> createGame(
        @RequestPart("request") @Valid GameCreateRequest request,
        @RequestPart("eeg_data_file") MultipartFile eegDataFile,
        @RequestPart("eye_data_file") MultipartFile eyeDataFile,
        @UserAuth Integer userId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "게임 리스트 조회")
    @GetMapping("/games")
    ResponseEntity<List<Object>> getGames(
        @UserAuth Integer userId,
        @RequestParam GameCategory gameCategory
    );
}
