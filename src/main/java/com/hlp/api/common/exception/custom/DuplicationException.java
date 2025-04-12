package com.hlp.api.common.exception.custom;

public abstract class DuplicationException extends HlpException {
    protected DuplicationException(String detail) {
        super(detail);
    }

    protected DuplicationException(String message, String detail) {
        super(message, detail);
    }
}
