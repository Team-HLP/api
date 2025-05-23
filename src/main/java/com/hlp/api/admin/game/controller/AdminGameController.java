package com.hlp.api.admin.game.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hlp.api.admin.game.dto.response.AdminGameDetailResponse;
import com.hlp.api.admin.game.dto.response.AdminGameResponse;
import com.hlp.api.admin.game.dto.response.AdminGameStatisticsResponse;
import com.hlp.api.admin.game.service.AdminGameService;
import com.hlp.api.common.auth.admin.AdminAuth;
import com.hlp.api.domain.guardian.dto.response.ChildADHDStatisticsResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminGameController implements AdminGameApi {

    private final AdminGameService adminGameService;

    @GetMapping("/admin/game")
    public ResponseEntity<List<AdminGameResponse>> getGames(
        @RequestParam(name = "user_id") Integer userId,
        @AdminAuth Integer adminId
    ) {
        List<AdminGameResponse> response = adminGameService.getGames(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/game/{gameId}")
    public ResponseEntity<AdminGameDetailResponse> getGame(
        @PathVariable(name = "gameId") Integer gameId,
        @RequestParam(name = "user_id") Integer userId,
        @AdminAuth Integer adminId
    ) {
        AdminGameDetailResponse response = adminGameService.getGame(userId, gameId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/game/statistics")
    public ResponseEntity<List<AdminGameStatisticsResponse>> getGameStatistics(
        @RequestParam(name = "user_id") Integer userId,
        @AdminAuth Integer adminId
    ) {
        List<AdminGameStatisticsResponse> response = adminGameService.getGameStatistics(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/game/statistics/bio")
    public ResponseEntity<List<ChildADHDStatisticsResponse>> getGameStatisticsByBio(
        @RequestParam(name = "user_id") Integer userId,
        @AdminAuth Integer adminId
    ) {
        List<ChildADHDStatisticsResponse> response = adminGameService.getChildrenGames(userId);
        return ResponseEntity.ok(response);
    }
}
