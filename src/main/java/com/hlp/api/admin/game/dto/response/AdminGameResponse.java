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
    @Schema(description = "게임 번호", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(description = "게임 카테고리", example = "운석 부수기", requiredMode = REQUIRED)
    String category,

    @Schema(description = "게임 생성일", example = "2025.04.12 14:12")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    LocalDateTime createdAt
) {
    public static AdminGameResponse of(Game game) {
        return new AdminGameResponse(
            game.getId(),
            game.getGameCategory().getDisplayName(),
            game.getCreatedAt()
        );
    }
}
