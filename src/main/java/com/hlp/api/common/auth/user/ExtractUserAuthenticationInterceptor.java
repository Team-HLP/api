package com.hlp.api.common.auth.user;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hlp.api.common.auth.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExtractUserAuthenticationInterceptor implements HandlerInterceptor {

    private static final String BEARER_TYPE = "Bearer ";
    private static final int BEARER_TYPE_LEN = 7;

    private final JwtProvider jwtProvider;
    private final UserAuthContext userAuthContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional.ofNullable(extractAccessToken(request))
            .ifPresent(token -> {
                Integer userId = jwtProvider.getUserId(token);
                userAuthContext.setUserId(userId);
            });
        return true;
    }

    public static String extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(BEARER_TYPE_LEN);
        }
        return null;
    }
}
