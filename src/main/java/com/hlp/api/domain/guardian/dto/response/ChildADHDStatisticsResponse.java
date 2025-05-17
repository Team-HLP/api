package com.hlp.api.domain.guardian.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.guardian.model.ADHDStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record ChildADHDStatisticsResponse(
    @Schema(description = "충동 억제 점수", example = "48", requiredMode = REQUIRED)
    Double impulseInhibitionScore,

    @Schema(description = "집중력 점수", example = "45", requiredMode = REQUIRED)
    Double concentrationScore,

    @Schema(description = "ADHD 상태", example = "양호", requiredMode = REQUIRED)
    String adhdStatus
) {
    public static ChildADHDStatisticsResponse from(Double impulseInhibitionScore, Double concentrationScore, ADHDStatus ADHDStatus) {
        return new ChildADHDStatisticsResponse(
            roundToTwoDecimals(impulseInhibitionScore),
            roundToTwoDecimals(concentrationScore),
            ADHDStatus.getDescription()
        );
    }

    private static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
