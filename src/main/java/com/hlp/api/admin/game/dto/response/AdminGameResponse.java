package com.hlp.api.admin.game.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.game.model.Game;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record AdminGameResponse(
    @Schema(name = "게임 번호", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(name = "평균 왼쪽 동공 크기", example = "3.57", requiredMode = REQUIRED)
    Float avgLeftEyePupilSize,

    @Schema(name = "평균 오른쪽 동공 크기", example = "3.57", requiredMode = REQUIRED)
    Float avgRightEyePupilSize,

    @Schema(name = "총 눈 깜빡임 횟수", example = "123", requiredMode = REQUIRED)
    Integer blinkEyeCount,

    @Schema(description = "게임 생성일", example = "2025.04.12 14:12")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    LocalDateTime createdAt
) {
    public static AdminGameResponse of(Game game) {
        return new AdminGameResponse(
            game.getId(),
            game.getAvgLeftEyePupilSize(),
            game.getAvgRightEyePupilSize(),
            game.getBlinkEyeCount(),
            game.getCreatedAt()
        );
    }
}
