package com.hlp.api.admin.game.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record AdminGameStatisticsResponse(
    @Schema(description = "눈 깜빡임 횟수", example = "1", requiredMode = REQUIRED)
    Integer eyeBlinkCount,

    @Schema(description = "평균 동공 크기", requiredMode = REQUIRED)
    AvgEyePupilSize avgEyePupilSize,

    @Schema(description = "평균 동공 크기 오차 범위 이하로 떨어진 횟수", requiredMode = REQUIRED)
    BelowAvgEyePupilSizeCount belowAvgEyePupilSizeCount,

    @Schema(description = "TBR 계산된 점수", example = "100.0", requiredMode = REQUIRED)
    Double TBRConversionScore,

    @Schema(description = "게임 생성일", example = "2025.04.12 14:12")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    LocalDateTime createdAt
) {
    public record AvgEyePupilSize(
        Double left,
        Double right
    ) {

    }

    public record BelowAvgEyePupilSizeCount(
        Integer left,
        Integer right
    ) {

    }

    public static AdminGameStatisticsResponse of(
        Integer eyeBlinkCount,
        Double avgEyePupilLeftSize,
        Double avgEyePupilRightSize,
        Integer belowLeftAvgEyePupilSizeCount,
        Integer belowRightAvgEyePupilSizeCount,
        Double TBRConversionScore,
        LocalDateTime createdAt
    ) {
        return new AdminGameStatisticsResponse(
            eyeBlinkCount,
            new AvgEyePupilSize(
                avgEyePupilLeftSize,
                avgEyePupilRightSize
            ),
            new BelowAvgEyePupilSizeCount(
                belowLeftAvgEyePupilSizeCount,
                belowRightAvgEyePupilSizeCount
            ),
            TBRConversionScore,
            createdAt
        );
    }
}
