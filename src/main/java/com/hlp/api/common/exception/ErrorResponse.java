package com.hlp.api.common.exception;

public record ErrorResponse(
    int status,
    String message
) {

}

