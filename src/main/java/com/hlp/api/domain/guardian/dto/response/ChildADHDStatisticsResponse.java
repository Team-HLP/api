package com.hlp.api.domain.guardian.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.guardian.model.ADHDStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record ChildADHDStatisticsResponse(
    @Schema(description = "충동 억제 점수", example = "48", requiredMode = REQUIRED)
    Integer impulseInhibitionScore,

    @Schema(description = "집중력 점수", example = "45", requiredMode = REQUIRED)
    Integer concentrationScore,

    @Schema(description = "ADHD 상태", example = "양호", requiredMode = REQUIRED)
    String ADHDStatus
) {
    public static ChildADHDStatisticsResponse from(Integer impulseInhibitionScore, Integer concentrationScore, ADHDStatus ADHDStatus) {
        return new ChildADHDStatisticsResponse(
            impulseInhibitionScore,
            concentrationScore,
            ADHDStatus.getDescription()
        );
    }
}
