package com.hlp.api.admin.user.dto.request;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.user.model.Sex;
import com.hlp.api.domain.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = SnakeCaseStrategy.class)
public record UserProvideRequest(
    @Schema(description = "신관규", example = "이름", requiredMode = REQUIRED)
    @NotBlank(message = "이름을 입력해주세요.")
    String name,

    @Schema(description = "SHA 256 해시 알고리즘으로 암호화된 전화번호", example = "e60124f2fe2045215abda1ae912aa80bb66dab5fc231a758387682c9c0e70c01", requiredMode = REQUIRED)
    @NotBlank(message = "전화번호를 입력해주세요.")
    String phoneNumber,

    @Schema(description = "나이", example = "25", requiredMode = REQUIRED)
    @NotNull(message = "나이를 입력해주세요.")
    Integer age,

    @Schema(description = "성별", example = "MAN", requiredMode = REQUIRED)
    @NotNull(message = "성별를 입력해주세요.")
    Sex sex
) {
    public User toEntity(String loginId, String password) {
        return User.builder()
            .loginId(loginId)
            .password(password)
            .phoneNumber(phoneNumber)
            .name(name)
            .age(age)
            .sex(sex)
            .build();
    }
}
