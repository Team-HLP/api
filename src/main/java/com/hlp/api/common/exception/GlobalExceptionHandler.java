package com.hlp.api.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hlp.api.common.exception.custom.AuthenticationException;
import com.hlp.api.common.exception.custom.DataNotFoundException;
import com.hlp.api.common.exception.custom.DuplicationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException e) {
        return buildErrorResponse(NOT_FOUND, e.getDetail());
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<Object> handleDuplicationException(DuplicationException e) {
        return buildErrorResponse(BAD_REQUEST, e.getDetail());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
        return buildErrorResponse(UNAUTHORIZED, e.getDetail());
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponse response = new ErrorResponse(httpStatus.value(), message);
        return ResponseEntity.status(httpStatus).body(response);
    }
}
