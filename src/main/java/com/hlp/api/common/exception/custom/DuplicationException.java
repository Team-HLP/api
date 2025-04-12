package com.hlp.api.common.exception.custom;

public abstract class DuplicationException extends HlpException {
    protected DuplicationException(String message, String detail) {
        super(message, detail);
    }
}
