package com.hlp.api.domain.user.exception;

import com.hlp.api.common.exception.custom.DuplicationException;

public class UserLoginIdDuplicateException extends DuplicationException {
    public UserLoginIdDuplicateException(String detail) {
        super(detail);
    }

    public UserLoginIdDuplicateException(String message, String detail) {
        super(message, detail);
    }
}
