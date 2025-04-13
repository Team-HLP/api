package com.hlp.api.admin.game.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.hlp.api.admin.game.dto.response.AdminGameResponse;
import com.hlp.api.common.auth.admin.AdminAuth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "(Admin) Game: 게임", description = "어드민 게임 API")
public interface AdminGameApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "게임 리스트 조회")
    @GetMapping("/admin/game")
    ResponseEntity<List<AdminGameResponse>> getGames(
        @RequestParam(name = "user_id") Integer userId,
        @AdminAuth Integer adminId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "게임 단건 조회")
    @GetMapping("/admin/game/{gameId}")
    ResponseEntity<AdminGameResponse> getGame(
        @PathVariable(name = "gameId") Integer gameId,
        @RequestParam(name = "user_id") Integer userId,
        @AdminAuth Integer adminId
    );
}
