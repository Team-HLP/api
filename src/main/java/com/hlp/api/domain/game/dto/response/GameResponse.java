package com.hlp.api.domain.game.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.game.model.Game;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GameResponse(
    @Schema(name = "게임 id", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(name = "결과", example = "성공", requiredMode = REQUIRED)
    String result,

    @Schema(name = "점수", example = "1234", requiredMode = REQUIRED)
    Integer score,

    @Schema(name = "체력", example = "55", requiredMode = REQUIRED)
    Integer hp,

    @Schema(name = "운석 파괴 수", example = "54", requiredMode = REQUIRED)
    Integer meteoriteBrokenCount,

    @Schema(description = "게임 생성일", example = "2025.04.12 14:12")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    LocalDateTime createdAt
) {
    public static GameResponse of(Game game) {
        return new GameResponse(
            game.getId(),
            game.getResult().getDescription(),
            game.getScore(),
            game.hashCode(),
            game.getMeteoriteBrokenCount(),
            game.getCreatedAt()
        );
    }
}
