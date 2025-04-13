package com.hlp.api.admin.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hlp.api.admin.user.dto.request.AdminLoginRequest;
import com.hlp.api.admin.user.dto.response.AdminLoginResponse;
import com.hlp.api.admin.user.service.AdminUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserController implements AdminUserApi {

    private final AdminUserService adminUserService;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(
        @RequestBody @Valid AdminLoginRequest request
    ) {
        AdminLoginResponse response = adminUserService.login(request);
        return ResponseEntity.ok(response);
    }
}
