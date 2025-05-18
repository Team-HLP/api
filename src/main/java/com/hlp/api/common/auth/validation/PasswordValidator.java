package com.hlp.api.common.auth.validation;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hlp.api.domain.user.exception.NotMatchPasswordException;

public final class PasswordValidator {

    private PasswordValidator() {}

    public static void checkPasswordMatches(PasswordEncoder encoder, String rawPassword, String encodedPassword) {
        if (!encoder.matches(rawPassword, encodedPassword)) {
            throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }
}
