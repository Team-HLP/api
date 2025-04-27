package com.hlp.api.admin.game.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.admin.game.model.EyeData;

@JsonNaming(value = SnakeCaseStrategy.class)
public record AdminGameDetailResponse(
    EyeData eyeData
) {
    public static AdminGameDetailResponse of(EyeData eyeData) {
        return new AdminGameDetailResponse(eyeData);
    }
}
