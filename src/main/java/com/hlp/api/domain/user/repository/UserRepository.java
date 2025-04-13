package com.hlp.api.domain.user.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.hlp.api.common.auth.exception.UserNotFoundException;
import com.hlp.api.domain.user.model.User;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    default User getByLoginId(String loginId) {
        return findByLoginId(loginId)
            .orElseThrow(() -> new UserNotFoundException("등록되지 않은 아이디입니다"));
    }

    void save(User user);

    Integer count();
}
