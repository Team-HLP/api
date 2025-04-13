package com.hlp.api.admin.user.exception;

import com.hlp.api.common.exception.custom.DataNotFoundException;

public class AdminUserNotFoundException extends DataNotFoundException {
    public AdminUserNotFoundException(String detail) {
        super(detail);
    }

    public AdminUserNotFoundException(String message, String detail) {
        super(message, detail);
    }
}
