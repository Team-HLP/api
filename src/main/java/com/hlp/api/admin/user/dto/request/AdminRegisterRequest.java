package com.hlp.api.admin.user.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.hlp.api.admin.user.model.Admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AdminRegisterRequest(
    @Schema(description = "아이디", example = "user00001", requiredMode = REQUIRED)
    @NotBlank(message = "아이디를 입력해주세요.")
    String loginId,

    @Schema(description = "SHA 256 해시 알고리즘으로 암호화된 비밀번호", example = "bc19d3b3c9e45818c670965f21e9a65e2cb6ef2b91265dbb4baa82124977a58d", requiredMode = REQUIRED)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password,

    @Schema(description = "이름", example = "신관규", requiredMode = REQUIRED)
    @NotBlank(message = "이름을 입력해주세요.")
    String name,

    @Schema(description = "전화번호", example = "010-1234-5678", requiredMode = REQUIRED)
    @NotBlank(message = "전화번호를 입력해주세요.")
    String phoneNumber
) {
    public Admin toEntity(String hashPassword) {
        return Admin.builder()
            .loginId(loginId)
            .password(hashPassword)
            .isAuth(false)
            .name(name)
            .phoneNumber(phoneNumber)
            .build();
    }
}
