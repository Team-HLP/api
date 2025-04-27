package com.hlp.api.admin.game.exception;

import com.hlp.api.common.exception.custom.HlpException;

public class DataFileReadException extends HlpException {
    public DataFileReadException(String detail) {
        super(detail);
    }

    public DataFileReadException(String message, String detail) {
        super(message, detail);
    }
}
