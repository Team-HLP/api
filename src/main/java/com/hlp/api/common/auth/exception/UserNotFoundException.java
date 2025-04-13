package com.hlp.api.common.auth.exception;

import com.hlp.api.common.exception.custom.DataNotFoundException;

public class UserNotFoundException extends DataNotFoundException {
    public UserNotFoundException(String detail) {
        super(detail);
    }

    public UserNotFoundException(String message, String detail) {
        super(message, detail);
    }
}
