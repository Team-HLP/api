package com.hlp.api.admin.user.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record UserProvideResponse(
    @Schema(description = "로그인 아이디", example = "user00001", requiredMode = REQUIRED)
    String loginId
) {
    public static UserProvideResponse of(String loginId) {
        return new UserProvideResponse(loginId);
    }
}
