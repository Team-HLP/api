package com.hlp.api.common.exception.custom;

public abstract class DataNotFoundException extends HlpException {
    protected DataNotFoundException(String detail) {
        super(detail);
    }

    protected DataNotFoundException(String message, String detail) {
        super(message, detail);
    }
}
