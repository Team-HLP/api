package com.hlp.api.domain.user.service;

import static com.hlp.api.common.auth.validation.PasswordValidator.checkPasswordMatches;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlp.api.common.auth.JwtProvider;
import com.hlp.api.domain.user.dto.request.UserLoginRequest;
import com.hlp.api.domain.user.dto.response.UserLoginResponse;
import com.hlp.api.domain.user.exception.UserWithDrawException;
import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.getByLoginId(request.loginId());
        if (user.getIsDeleted()) {
            throw new UserWithDrawException("등록되지 않은 아이디입니다.");
        }
        checkPasswordMatches(passwordEncoder, request.password(), user.getPassword());
        String accessToken = jwtProvider.createToken(user);
        return UserLoginResponse.of(accessToken);
    }
}
