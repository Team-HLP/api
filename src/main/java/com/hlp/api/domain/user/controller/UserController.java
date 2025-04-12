package com.hlp.api.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hlp.api.domain.user.dto.request.UserLoginRequest;
import com.hlp.api.domain.user.dto.response.UserLoginResponse;
import com.hlp.api.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserApi {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
        @RequestBody @Valid UserLoginRequest request
    ) {
        UserLoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
