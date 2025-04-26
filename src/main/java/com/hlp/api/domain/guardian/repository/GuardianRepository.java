package com.hlp.api.domain.guardian.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.hlp.api.common.auth.exception.UserNotFoundException;
import com.hlp.api.domain.guardian.model.Guardian;

public interface GuardianRepository extends Repository<Guardian, Integer> {

    Optional<Guardian> findByLoginId(String loginId);

    default Guardian getByLoginId(String loginId) {
        return findByLoginId(loginId)
            .orElseThrow(() -> new UserNotFoundException("등록되지 않은 아이디입니다"));
    }

}
