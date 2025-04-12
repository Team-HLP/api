package com.hlp.api.domain.user.exception;

import com.hlp.api.common.exception.custom.AuthenticationException;

public class NotMatchPasswordException extends AuthenticationException {
    public NotMatchPasswordException(String detail) {
        super(detail);
    }

    public NotMatchPasswordException(String message, String detail) {
        super(message, detail);
    }
}
