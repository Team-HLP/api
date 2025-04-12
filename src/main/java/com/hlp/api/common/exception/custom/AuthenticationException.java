package com.hlp.api.common.exception.custom;

public abstract class AuthenticationException extends HlpException {
    protected AuthenticationException(String detail) {
        super(detail);
    }

    protected AuthenticationException(String message, String detail) {
        super(message, detail);
    }
}
