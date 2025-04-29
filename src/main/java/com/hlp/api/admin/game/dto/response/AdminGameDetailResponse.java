package com.hlp.api.admin.game.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.admin.game.model.EegData;
import com.hlp.api.admin.game.model.EyeData;

@JsonNaming(value = SnakeCaseStrategy.class)
public record AdminGameDetailResponse(
    EyeData eyeData,
    List<EegData> eegData
) {
    public static AdminGameDetailResponse of(EyeData eyeData, List<EegData> eegData) {
        return new AdminGameDetailResponse(eyeData, eegData);
    }
}
