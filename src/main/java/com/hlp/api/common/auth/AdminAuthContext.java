package com.hlp.api.common.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.hlp.api.common.auth.exception.UserNotFoundException;

import lombok.Setter;

@Setter
@Component
@RequestScope
public class AdminAuthContext {

    private Integer userId;

    public Integer getUserId() {
        if (userId == null) {
            throw new UserNotFoundException("userId is null");
        }
        return userId;
    }
}
