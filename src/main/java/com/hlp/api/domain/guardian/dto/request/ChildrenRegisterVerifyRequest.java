package com.hlp.api.domain.guardian.dto.request;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonNaming(value = SnakeCaseStrategy.class)
public record ChildrenRegisterVerifyRequest(
    @Schema(description = "자녀 로그인 id", example = "user00001", requiredMode = REQUIRED)
    @NotNull(message = "아이디를 입력해주세요.")
    String childrenId,

    @NotBlank(message = "인증번호를 입력해주세요.")
    @Digits(integer = 6, fraction = 0, message = "인증번호는 6자리 숫자여야 합니다.")
    @Schema(description = "인증번호", example = "123456", requiredMode = REQUIRED)
    String certificationCode
){

}
