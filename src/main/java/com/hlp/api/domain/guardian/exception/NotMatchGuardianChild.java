package com.hlp.api.domain.guardian.exception;

import com.hlp.api.common.exception.custom.DataNotFoundException;

public class NotMatchGuardianChild extends DataNotFoundException {
    public NotMatchGuardianChild(String detail) {
        super(detail);
    }

    public NotMatchGuardianChild(String message, String detail) {
        super(message, detail);
    }
}
