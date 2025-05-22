package com.hlp.api.domain.guardian.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hlp.api.common.auth.guardian.GuardianAuth;
import com.hlp.api.domain.guardian.dto.request.ChildrenRegisterVerifyRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianChildrenRegisterRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianLoginRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianRegisterRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianVerificationRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianVerifySmsVerificationRequest;
import com.hlp.api.domain.guardian.dto.response.ChildADHDStatisticsResponse;
import com.hlp.api.domain.guardian.dto.response.ChildrenGameResponse;
import com.hlp.api.domain.guardian.dto.response.ChildrenResponse;
import com.hlp.api.domain.guardian.dto.response.GuardianLoginResponse;
import com.hlp.api.domain.guardian.dto.response.GuardianResponse;
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

    @PostMapping("/sms/send")
    public ResponseEntity<Void> sendSmsVerificationCode(
        @RequestBody @Valid GuardianVerificationRequest request
    ) {
        guardianService.sendCertificationCode(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sms/verify")
    public ResponseEntity<Void> verifySmsVerificationCode(
        @RequestBody @Valid GuardianVerifySmsVerificationRequest request
    ) {
        guardianService.verifySmsVerificationCode(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
        @RequestBody @Valid GuardianRegisterRequest request
    ) {
        guardianService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/me")
    public ResponseEntity<GuardianResponse> getGuardian(
        @GuardianAuth Integer guardianId
    ) {
        GuardianResponse response = guardianService.getGuardian(guardianId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/children/{childrenId}")
    public ResponseEntity<ChildrenResponse> getChild(
        @PathVariable(name = "childrenId") Integer childrenId,
        @GuardianAuth Integer guardianId
    ) {
        ChildrenResponse responses = guardianService.getChild(childrenId, guardianId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/children")
    public ResponseEntity<Void> registerChildren(
        @RequestBody @Valid GuardianChildrenRegisterRequest request,
        @GuardianAuth Integer guardianId
    ) {
        guardianService.registerChildren(guardianId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/children/verify")
    public ResponseEntity<Void> verifyChildrenRegister(
        @RequestBody @Valid ChildrenRegisterVerifyRequest request,
        @GuardianAuth Integer guardianId
    ) {
        guardianService.verifyChildrenRegister(guardianId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/children")
    public ResponseEntity<List<ChildrenResponse>> getChildren(
        @GuardianAuth Integer guardianId
    ) {
        List<ChildrenResponse> response = guardianService.getChildren(guardianId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/children/{childId}/games")
    public ResponseEntity<List<ChildrenGameResponse>> getChildrenGames(
        @PathVariable Integer childId,
        @GuardianAuth Integer guardianId
    ) {
        List<ChildrenGameResponse> response = guardianService.getChildrenGames(guardianId, childId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/children/{childrenId}/statistics/{gameId}")
    public ResponseEntity<ChildADHDStatisticsResponse> getChildADHDStatistics(
        @PathVariable(name = "childrenId") Integer childrenId,
        @PathVariable(name = "gameId") Integer gameId,
        @GuardianAuth Integer guardianId
    ) {
        ChildADHDStatisticsResponse response = guardianService.getChildADHDStatistics(gameId, childrenId, guardianId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/children/{childrenId}/statistics")
    public ResponseEntity<List<ChildADHDStatisticsResponse>> getChildADHDStatistics(
        @PathVariable(name = "childrenId") Integer childrenId,
        @GuardianAuth Integer guardianId
    ) {
        List<ChildADHDStatisticsResponse> response = guardianService.getChildADHDStatistics(childrenId, guardianId);
        return ResponseEntity.ok(response);
    }
}
