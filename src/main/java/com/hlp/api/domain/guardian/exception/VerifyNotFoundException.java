package com.hlp.api.domain.guardian.exception;

import com.hlp.api.common.exception.custom.DataNotFoundException;

public class VerifyNotFoundException extends DataNotFoundException {
    public VerifyNotFoundException(String detail) {
        super(detail);
    }

    public VerifyNotFoundException(String message, String detail) {
        super(message, detail);
    }
}
