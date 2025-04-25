package com.hlp.api.domain.user.exception;

import com.hlp.api.common.exception.custom.AuthenticationException;

public class UserWithDrawException extends AuthenticationException {
  public UserWithDrawException(String detail) {
    super(detail);
  }

  public UserWithDrawException(String message, String detail) {
    super(message, detail);
  }
}
