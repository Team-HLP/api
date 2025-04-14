package com.hlp.api.admin.user.service;

import static com.hlp.api.common.auth.validation.PasswordValidator.checkPasswordMatches;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlp.api.admin.game.repository.AdminGameRepository;
import com.hlp.api.admin.user.dto.request.AdminLoginRequest;
import com.hlp.api.admin.user.dto.request.AdminRegisterRequest;
import com.hlp.api.admin.user.dto.request.UserProvideRequest;
import com.hlp.api.admin.user.dto.response.AdminLoginResponse;
import com.hlp.api.admin.user.dto.response.UserProvideResponse;
import com.hlp.api.admin.user.dto.response.UserResponse;
import com.hlp.api.admin.user.model.Admin;
import com.hlp.api.admin.user.repository.AdminUserRepository;
import com.hlp.api.common.auth.JwtProvider;
import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final AdminGameRepository adminGameRepository;
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
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

    @Transactional
    public UserProvideResponse provide(UserProvideRequest request) {
        String password = passwordEncoder.encode(request.phoneNumber().substring(request.phoneNumber().length() - 8));
        Integer count = userRepository.count();
        String loginId = String.format("user%05d", count + 1);
        userRepository.save(request.toEntity(loginId, password));

        return UserProvideResponse.of(loginId);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
            .map(UserResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void userWithdraw(Integer userId) {
        User user = userRepository.getById(userId);
        adminGameRepository.findAllByUserId(userId).forEach(adminGameRepository::delete);
        userRepository.delete(user);
    }
}
