package com.hlp.api.admin.game.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.admin.game.model.EegData;
import com.hlp.api.admin.game.model.EyeData;

@JsonNaming(value = SnakeCaseStrategy.class)
public record AdminGameDetailResponse(
    EyeData eyeData,
    List<EegData> eegData,
    @JsonFormat(pattern = "yy.MM.dd")
    LocalDate createdAt
) {
    public static AdminGameDetailResponse of(EyeData eyeData, List<EegData> eegData, LocalDateTime createdAt) {
        return new AdminGameDetailResponse(eyeData, eegData, createdAt.toLocalDate());
    }
}
