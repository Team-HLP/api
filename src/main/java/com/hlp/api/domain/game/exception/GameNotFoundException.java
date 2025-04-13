package com.hlp.api.domain.game.exception;

import com.hlp.api.common.exception.custom.DataNotFoundException;

public class GameNotFoundException extends DataNotFoundException {
    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(String message, String detail) {
        super(message, detail);
    }
}
