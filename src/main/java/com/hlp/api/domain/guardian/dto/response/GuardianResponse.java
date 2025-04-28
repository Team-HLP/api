package com.hlp.api.domain.guardian.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.guardian.model.Guardian;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GuardianResponse(
    @Schema(description = "고유 id", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(description = "로그인 아이디", example = "user00001", requiredMode = REQUIRED)
    String loginId,

    @Schema(description = "이름", example = "신관규", requiredMode = REQUIRED)
    String name,

    @Schema(description = "전화번호", example = "01012345678", requiredMode = REQUIRED)
    String phoneNumber,

    @Schema(description = "유저 생성일", example = "2025.03.23", requiredMode = REQUIRED)
    @JsonFormat(pattern = "yyyy.MM.dd")
    LocalDate createdAt
) {
    public static GuardianResponse of(Guardian guardian) {
        return new GuardianResponse(
            guardian.getId(),
            guardian.getLoginId(),
            guardian.getName(),
            guardian.getPhoneNumber(),
            guardian.getCreatedAt().toLocalDate()
        );
    }
}
