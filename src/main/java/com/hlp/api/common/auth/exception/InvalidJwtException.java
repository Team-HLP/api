package com.hlp.api.common.auth.exception;

import com.hlp.api.common.exception.custom.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {
    public InvalidJwtException(String message, String detail) {
        super(message, detail);
    }
}
