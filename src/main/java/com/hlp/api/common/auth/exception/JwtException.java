package com.hlp.api.common.auth.exception;

import com.hlp.api.common.exception.custom.AuthenticationException;

public class JwtException extends AuthenticationException {
    public JwtException(String message, String detail) {
        super(message, detail);
    }
}
