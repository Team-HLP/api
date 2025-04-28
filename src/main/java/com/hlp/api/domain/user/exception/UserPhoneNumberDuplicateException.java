package com.hlp.api.domain.user.exception;

import com.hlp.api.common.exception.custom.DuplicationException;

public class UserPhoneNumberDuplicateException extends DuplicationException {
    public UserPhoneNumberDuplicateException(String detail) {
        super(detail);
    }

    public UserPhoneNumberDuplicateException(String message, String detail) {
        super(message, detail);
    }
}
