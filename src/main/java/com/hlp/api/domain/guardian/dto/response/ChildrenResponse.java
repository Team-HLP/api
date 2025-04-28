package com.hlp.api.domain.guardian.dto.response;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record ChildrenResponse(
    @Schema(description = "고유 id", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(description = "이름", example = "신관규", requiredMode = REQUIRED)
    String name,

    @Schema(description = "나이", example = "24", requiredMode = REQUIRED)
    Integer age,

    @Schema(description = "성별", example = "남", requiredMode = REQUIRED)
    String sex
) {
    public static ChildrenResponse of(User user) {
        return new ChildrenResponse(
            user.getId(),
            user.getName(),
            user.getAge(),
            user.getSex().getDescription()
        );
    }
}
