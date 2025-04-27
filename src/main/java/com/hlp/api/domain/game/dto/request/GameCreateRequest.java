package com.hlp.api.domain.game.dto.request;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.model.Result;
import com.hlp.api.domain.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GameCreateRequest(
    @Schema(description = "결과", example = "SUCCESS", requiredMode = REQUIRED)
    @NotNull(message = "결과를 입력해주세요.")
    Result result,

    @Schema(description = "점수", example = "1234", requiredMode = REQUIRED)
    @NotNull(message = "점수를 입력해주세요.")
    Integer score,

    @Schema(description = "체력", example = "55", requiredMode = REQUIRED)
    @NotNull(message = "체력을 입력해주세요.")
    Integer hp,

    @Schema(description = "운석 파괴 수", example = "54", requiredMode = REQUIRED)
    @NotNull(message = "운석 파괴 수를 입력해주세요.")
    Integer meteoriteBrokenCount,

    @Schema(description = "눈 깜빡임 횟수", example = "12", requiredMode = REQUIRED)
    @NotNull(message = "눈 깜빡임 횟수를 입력해주세요.")
    Integer blinkEyeCount,

    @Schema(description = "왼쪽 눈 동공 평균 크기", example = "3.15", requiredMode = REQUIRED)
    @NotNull(message = "왼쪽 눈 동공 평균 크기를 입력해주세요.")
    Float avgLeftEyePupilSize,

    @Schema(description = "오른쪽 눈 동공 평균 크기", example = "3.15", requiredMode = REQUIRED)
    @NotNull(message = "오른쪽 눈 동공 평균 크기를 입력해주세요.")
    Float avgRightEyePupilSize

) {
    public Game toEntity(User user) {
        return Game.builder()
            .result(result)
            .blinkEyeCount(blinkEyeCount)
            .avgLeftEyePupilSize(avgLeftEyePupilSize)
            .avgRightEyePupilSize(avgRightEyePupilSize)
            .user(user)
            .build();
    }
}
