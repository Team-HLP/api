package com.hlp.api.admin.user.service;

import static com.hlp.api.common.auth.validation.PasswordValidator.checkPasswordMatches;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlp.api.admin.user.dto.request.AdminLoginRequest;
import com.hlp.api.admin.user.dto.request.AdminRegisterRequest;
import com.hlp.api.admin.user.dto.response.AdminLoginResponse;
import com.hlp.api.admin.user.model.Admin;
import com.hlp.api.admin.user.repository.AdminUserRepository;
import com.hlp.api.common.auth.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public AdminLoginResponse login(AdminLoginRequest request) {
        Admin admin = adminUserRepository.getByLoginId(request.loginId());
        checkPasswordMatches(passwordEncoder, request.password(), admin.getPassword());
        String accessToken = jwtProvider.createToken(admin);
        return AdminLoginResponse.of(accessToken);
    }

    @Transactional
    public void register(AdminRegisterRequest request) {
        Admin admin = request.toEntity(passwordEncoder.encode(request.password()));
        adminUserRepository.save(admin);
    }
}
