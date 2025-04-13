package com.hlp.api.admin.user.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlp.api.domain.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponse(
    @Schema(description = "로그인 아이디", example = "user00001", requiredMode = REQUIRED)
    String loginId,

    @Schema(description = "이름", example = "신관규", requiredMode = REQUIRED)
    String name,

    @Schema(description = "나이", example = "25", requiredMode = REQUIRED)
    Integer age,

    @Schema(description = "성별", example = "남자", requiredMode = REQUIRED)
    String sex,

    @Schema(description = "유저 생성일", example = "2025.03.23", requiredMode = REQUIRED)
    @JsonFormat(pattern = "yyyy.MM.dd")
    LocalDate createdAt
) {
    public static UserResponse of(User user) {
        return new UserResponse(
            user.getLoginId(),
            user.getName(),
            user.getAge(),
            user.getSex().getDescription(),
            user.getCreatedAt().toLocalDate()
        );
    }
}
