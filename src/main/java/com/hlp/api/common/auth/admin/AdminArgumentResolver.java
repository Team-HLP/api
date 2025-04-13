package com.hlp.api.common.auth.admin;

import static java.util.Objects.requireNonNull;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hlp.api.admin.user.model.Admin;
import com.hlp.api.admin.user.repository.AdminUserRepository;
import com.hlp.api.common.auth.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminArgumentResolver implements HandlerMethodArgumentResolver {

    private final AdminUserRepository adminUserRepository;
    private final AdminAuthContext adminAuthContext;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AdminAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        AdminAuth adminAt = parameter.getParameterAnnotation(AdminAuth.class);
        requireNonNull(adminAt);

        Integer adminId = adminAuthContext.getUserId();
        Admin admin = adminUserRepository.getById(adminId);
        if (!admin.getIsAuth()) {
            throw new UserNotFoundException("비활성화 상태입니다");
        }

        return admin.getId();
    }
}
