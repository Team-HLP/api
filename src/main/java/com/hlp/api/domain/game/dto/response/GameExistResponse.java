package com.hlp.api.domain.game.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GameExistResponse(
    @Schema(description = "조회 여부", example = "true", requiredMode = REQUIRED)
    Boolean exist
) {
    public static GameExistResponse of(boolean exist) {
        return new GameExistResponse(exist);
    }
}
