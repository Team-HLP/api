package com.hlp.api.domain.user.exception;

import com.hlp.api.common.exception.custom.HlpException;

public class UserNotFoundException extends HlpException {
    public UserNotFoundException(String detail) {
        super(detail);
    }

    public UserNotFoundException(String message, String detail) {
        super(message, detail);
    }
}
