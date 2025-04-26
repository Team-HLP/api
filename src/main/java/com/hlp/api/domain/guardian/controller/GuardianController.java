package com.hlp.api.domain.guardian.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hlp.api.domain.guardian.dto.request.GuardianLoginRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianVerificationRequest;
import com.hlp.api.domain.guardian.dto.response.GuardianLoginResponse;
import com.hlp.api.domain.guardian.service.GuardianService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guardian")
public class GuardianController implements GuardianApi {

    private final GuardianService guardianService;

    @PostMapping("/login")
    public ResponseEntity<GuardianLoginResponse> login(
        @RequestBody @Valid GuardianLoginRequest request
    ) {
        GuardianLoginResponse response = guardianService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verification")
    public ResponseEntity<Void> verification(
        @RequestBody @Valid GuardianVerificationRequest request
    ) {
        guardianService.sendCertificationCode(request);
        return ResponseEntity.ok().build();
    }
}
