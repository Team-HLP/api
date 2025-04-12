package com.hlp.api.common.exception.custom;

public abstract class HlpException extends RuntimeException {
    protected final String detail;

    protected HlpException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public String getDetail() {
        return String.format("%s %s", getMessage(), detail);
    }
}
