package com.hlp.api.admin.user.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.hlp.api.admin.user.model.Admin;
import com.hlp.api.common.auth.exception.UserNotFoundException;

public interface AdminUserRepository extends Repository<Admin, Integer> {

    Optional<Admin> findByLoginId(String loginId);

    default Admin getByLoginId(String loginId) {
        return findByLoginId(loginId)
            .orElseThrow(() -> new UserNotFoundException("등록되지 않은 아이디입니다."));
    }

    void save(Admin admin);
}
