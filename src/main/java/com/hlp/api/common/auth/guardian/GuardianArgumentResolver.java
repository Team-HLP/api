package com.hlp.api.common.auth.guardian;

import static java.util.Objects.requireNonNull;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hlp.api.common.auth.user.UserAuth;
import com.hlp.api.common.auth.user.UserAuthContext;
import com.hlp.api.domain.guardian.model.Guardian;
import com.hlp.api.domain.guardian.repository.GuardianRepository;
import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GuardianArgumentResolver implements HandlerMethodArgumentResolver {

    private final GuardianRepository guardianRepository;
    private final UserAuthContext userAuthContext;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(GuardianAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        GuardianAuth guardianAuth = parameter.getParameterAnnotation(GuardianAuth.class);
        requireNonNull(guardianAuth);

        Integer userId = userAuthContext.getUserId();
        Guardian guardian = guardianRepository.getById(userId);
        return guardian.getId();
    }
}
