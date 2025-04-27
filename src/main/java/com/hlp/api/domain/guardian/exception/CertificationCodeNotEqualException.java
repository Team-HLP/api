package com.hlp.api.domain.guardian.exception;

import com.hlp.api.common.exception.custom.HlpException;

public class CertificationCodeNotEqualException extends HlpException {
    public CertificationCodeNotEqualException(String detail) {
        super(detail);
    }

    public CertificationCodeNotEqualException(String message, String detail) {
        super(message, detail);
    }
}
