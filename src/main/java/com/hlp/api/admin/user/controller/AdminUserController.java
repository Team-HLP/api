package com.hlp.api.admin.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hlp.api.admin.user.dto.request.AdminLoginRequest;
import com.hlp.api.admin.user.dto.request.AdminPasswordChangeRequest;
import com.hlp.api.admin.user.dto.request.AdminRegisterRequest;
import com.hlp.api.admin.user.dto.request.UserProvideRequest;
import com.hlp.api.admin.user.dto.response.AdminLoginResponse;
import com.hlp.api.admin.user.dto.response.UserProvideResponse;
import com.hlp.api.admin.user.dto.response.UserResponse;
import com.hlp.api.admin.user.service.AdminUserService;
import com.hlp.api.common.auth.admin.AdminAuth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminUserController implements AdminUserApi {

    private final AdminUserService adminUserService;

    @PostMapping("/admin/login")
    public ResponseEntity<AdminLoginResponse> login(
        @RequestBody @Valid AdminLoginRequest request
    ) {
        AdminLoginResponse response = adminUserService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<Void> register(
        @RequestBody @Valid AdminRegisterRequest request
    ) {
        adminUserService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/user/register")
    public ResponseEntity<UserProvideResponse> provide(
        @RequestBody @Valid UserProvideRequest request,
        @AdminAuth Integer adminId
    ) {
        UserProvideResponse response = adminUserService.provide(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserResponse>> getUsers(
        @AdminAuth Integer adminId
    ) {
        List<UserResponse> response = adminUserService.getUsers();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/user/withdraw")
    public ResponseEntity<Void> userWithdraw(
        @RequestParam(value = "userId") Integer userId,
        @AdminAuth Integer adminId
    ) {
        adminUserService.userWithdraw(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/password")
    public ResponseEntity<Void> changePassword(
        @RequestBody @Valid AdminPasswordChangeRequest request,
        @AdminAuth Integer adminId
    ) {
        adminUserService.changePassword(request, adminId);
        return ResponseEntity.noContent().build();
    }
}
