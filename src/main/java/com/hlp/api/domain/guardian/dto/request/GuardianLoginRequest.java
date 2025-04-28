package com.hlp.api.domain.guardian.dto.request;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GuardianLoginRequest(
    @Schema(description = "로그인 아이디", example = "user00001", requiredMode = REQUIRED)
    @NotBlank(message = "아이디를 입력해주세요.")
    String loginId,

    @Schema(description = "SHA 256 해시 알고리즘으로 암호화된 비밀번호", example = "bc19d3b3c9e45818c670965f21e9a65e2cb6ef2b91265dbb4baa82124977a58d", requiredMode = REQUIRED)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password
) {

}
