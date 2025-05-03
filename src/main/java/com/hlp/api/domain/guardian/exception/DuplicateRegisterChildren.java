package com.hlp.api.domain.guardian.exception;

import com.hlp.api.common.exception.custom.DuplicationException;

public class DuplicateRegisterChildren extends DuplicationException {
    public DuplicateRegisterChildren(String detail) {
        super(detail);
    }

    public DuplicateRegisterChildren(String message, String detail) {
        super(message, detail);
    }
}
