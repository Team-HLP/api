package com.hlp.api.domain.guardian.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "(Normal) Guardian: 보호자", description = "보호자 API")
@RequestMapping("/guardian")
public interface GuardianApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "로그인")
    @PostMapping("/login")
    ResponseEntity<GuardianLoginResponse> login(
        @RequestBody @Valid GuardianLoginRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "인증번호 발송")
    @PostMapping("/sms/send")
    ResponseEntity<Void> sendSmsVerificationCode(
        @RequestBody @Valid GuardianVerificationRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "인증번호 인증")
    @PostMapping("/sms/verify")
    ResponseEntity<Void> verifySmsVerificationCode(
        @RequestBody @Valid GuardianVerifySmsVerificationRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "보호자 회원가입")
    @PostMapping("/register")
    ResponseEntity<Void> register(
        @RequestBody @Valid GuardianRegisterRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "보호자 정보 조회")
    @PostMapping("/me")
    ResponseEntity<GuardianResponse> getGuardian(
        @GuardianAuth Integer guardianId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "등록된 자녀 단건 조회")
    @GetMapping("/children/{childrenId}")
    ResponseEntity<ChildrenResponse> getChild(
        @PathVariable(name = "childrenId") Integer childrenId,
        @GuardianAuth Integer guardianId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "자녀 등록 요청", description = """
        자녀 로그인 아이디를 입력해서 요청을 보내면, 자녀 정보에 등록된 전화번호로 인증번호가 전송됩니다.
        """)
    @PostMapping("/children")
    ResponseEntity<Void> registerChildren(
        @RequestBody @Valid GuardianChildrenRegisterRequest request,
        @GuardianAuth Integer guardianId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "자녀 등록 인증", description = """
        인증번호 인증에 성공하면, 자동으로 자녀 등록이 됩니다.
        """)
    @PostMapping("/children/verify")
    ResponseEntity<Void> verifyChildrenRegister(
        @RequestBody @Valid ChildrenRegisterVerifyRequest request,
        @GuardianAuth Integer guardianId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "등록된 자녀 리스트 조회")
    @GetMapping("/children")
    ResponseEntity<List<ChildrenResponse>> getChildren(
        @GuardianAuth Integer guardianId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "등록된 자녀의 게임 리스트 조회")
    @GetMapping("/children/{childId}/games")
    ResponseEntity<List<ChildrenGameResponse>> getChildrenGames(
        @PathVariable Integer childId,
        @GuardianAuth Integer guardianId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "자녀 ADHD 통계 단건 조회")
    @GetMapping("/children/{childrenId}/statistics/{gameId}")
    ResponseEntity<ChildADHDStatisticsResponse> getChildADHDStatistics(
        @PathVariable(name = "childrenId") Integer childrenId,
        @PathVariable(name = "gameId") Integer gameId,
        @GuardianAuth Integer guardianId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
        }
    )
    @Operation(summary = "자녀 ADHD 통계 리스트 조회")
    @GetMapping("/children/{childrenId}/statistics")
    ResponseEntity<List<ChildADHDStatisticsResponse>> getChildADHDStatistics(
        @PathVariable(name = "childrenId") Integer childrenId,
        @GuardianAuth Integer guardianId
    );
}
