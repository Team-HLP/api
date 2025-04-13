package com.hlp.api.admin.game.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hlp.api.admin.game.dto.response.AdminGameResponse;
import com.hlp.api.admin.game.service.AdminGameService;
import com.hlp.api.common.auth.admin.AdminAuth;

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
        List<AdminGameResponse> response = adminGameService.getGames(adminId);
        return ResponseEntity.ok(response);
    }
}
