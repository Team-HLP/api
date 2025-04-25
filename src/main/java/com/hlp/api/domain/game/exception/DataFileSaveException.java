package com.hlp.api.domain.game.exception;

import com.hlp.api.common.exception.custom.HlpException;

public class DataFileSaveException extends HlpException {
    public DataFileSaveException(String detail) {
        super(detail);
    }

    public DataFileSaveException(String message, String detail) {
        super(message, detail);
    }
}
