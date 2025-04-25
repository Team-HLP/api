package com.hlp.api.admin.user.dto.request;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.user.model.Sex;
import com.hlp.api.domain.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonNaming(value = SnakeCaseStrategy.class)
public record UserProvideRequest(
    @Schema(description = "신관규", example = "이름", requiredMode = REQUIRED)
    @NotBlank(message = "이름을 입력해주세요.")
    String name,

    @Schema(description = "전화번호", example = "01012345678", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^010\\d{8}$", message = "전화번호는 010으로 시작하고 뒤에 8자리 숫자여야 합니다.")
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
            .isDeleted(false)
            .build();
    }
}
