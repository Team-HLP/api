package com.hlp.api.common.auth.user;

import static java.util.Objects.requireNonNull;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;
    private final UserAuthContext userAuthContext;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        UserAuth userAuth = parameter.getParameterAnnotation(UserAuth.class);
        requireNonNull(userAuth);

        Integer userId = userAuthContext.getUserId();
        User user = userRepository.getById(userId);
        return user.getId();
    }
}
