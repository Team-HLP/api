package com.hlp.api.domain.game.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.model.MeteoriteDestruction;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record MeteoriteDestructionResponse(
    @Schema(description = "게임 id", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(description = "결과", example = "성공", requiredMode = REQUIRED)
    String result,

    @Schema(description = "점수", example = "1234", requiredMode = REQUIRED)
    Integer score,

    @Schema(description = "체력", example = "55", requiredMode = REQUIRED)
    Integer hp,

    @Schema(description = "운석 파괴 수", example = "54", requiredMode = REQUIRED)
    Integer meteoriteBrokenCount,

    @Schema(description = "게임 생성일", example = "2025.04.12 14:12")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    LocalDateTime createdAt
) {
    public static MeteoriteDestructionResponse of(Game game, MeteoriteDestruction meteoriteDestruction) {
        return new MeteoriteDestructionResponse(
            game.getId(),
            game.getResult().getDescription(),
            meteoriteDestruction.getScore(),
            meteoriteDestruction.getHp(),
            meteoriteDestruction.getMeteoriteBrokenCount(),
            game.getCreatedAt()
        );
    }
}
