package com.hlp.api.domain.guardian.dto.request;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.guardian.model.Guardian;
import com.hlp.api.domain.guardian.model.GuardianChildrenMap;
import com.hlp.api.domain.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GuardianChildrenRegisterRequest(
    @Schema(description = "자녀 고유 id", example = "1", requiredMode = REQUIRED)
    @NotNull(message = "아이디를 입력해주세요.")
    Integer childrenId
) {
    public GuardianChildrenMap toEntity(User children, Guardian guardian) {
        return GuardianChildrenMap.builder()
            .children(children)
            .guardian(guardian)
            .build();
    }
}
