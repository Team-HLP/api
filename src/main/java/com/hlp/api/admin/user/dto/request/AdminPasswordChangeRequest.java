package com.hlp.api.admin.user.dto.request;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(value = SnakeCaseStrategy.class)
public record AdminPasswordChangeRequest(
    @Schema(description = "기존 비밀번호", example = "bc19d3b3c9e45818c670965f21e9a65e2cb6ef2b91265dbb4baa82124977a58d", requiredMode = REQUIRED)
    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    String curPassword,

    @Schema(description = "새로운 비밀번호", example = "bc19d3b3c9e45818c670965f21e9a65e2cb6ef2b91265dbb4baa82124977a58d", requiredMode = REQUIRED)
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    String newPassword
) {

}
