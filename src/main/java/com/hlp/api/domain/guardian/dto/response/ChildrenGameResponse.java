package com.hlp.api.domain.guardian.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record ChildrenGameResponse(
    @Schema(description = "게임 Id", example = "1", requiredMode = REQUIRED)
    Integer gameId,

    @Schema(description = "인덱스", example = "1", requiredMode = REQUIRED)
    Integer index,

    @Schema(description = "ADHD 상태", example = "양호", requiredMode = REQUIRED)
    String adhdStatus,

    @Schema(description = "생성 일", example = "2025.03.23", requiredMode = REQUIRED)
    @JsonFormat(pattern = "yyyy.MM.dd")
    LocalDate createdAt
) {
    public static ChildrenGameResponse of(Integer gameId, Integer index, String adhdStatus, LocalDate createdAt) {
        return new ChildrenGameResponse(gameId, index, adhdStatus, createdAt);
    }
}
