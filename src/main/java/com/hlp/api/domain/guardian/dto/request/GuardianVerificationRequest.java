package com.hlp.api.domain.guardian.dto.request;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GuardianVerificationRequest(
    @Schema(description = "전화번호", example = "010-1234-5678", requiredMode = REQUIRED)
    @NotBlank(message = "전화번호를 입력해주세요.")
    String phoneNumber
) {
}
