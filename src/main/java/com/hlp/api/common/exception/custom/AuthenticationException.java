package com.hlp.api.common.exception.custom;

public abstract class AuthenticationException extends HlpException {
    protected AuthenticationException(String message, String detail) {
        super(message, detail);
    }
}
