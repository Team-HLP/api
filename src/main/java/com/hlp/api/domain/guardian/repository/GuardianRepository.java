package com.hlp.api.domain.guardian.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.hlp.api.common.auth.exception.UserNotFoundException;
import com.hlp.api.domain.guardian.model.Guardian;
import com.hlp.api.domain.user.exception.UserPhoneNumberDuplicateException;

public interface GuardianRepository extends Repository<Guardian, Integer> {

    Optional<Guardian> findByLoginId(String loginId);

    default Guardian getByLoginId(String loginId) {
        return findByLoginId(loginId)
            .orElseThrow(() -> new UserNotFoundException("등록되지 않은 아이디입니다"));
    }

    Optional<Guardian> findByPhoneNumber(String phoneNumber);

    default Guardian getByPhoneNumber(String phoneNumber) {
        return findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new UserPhoneNumberDuplicateException("등록된 전화번호입니다"));
    }

    void save(Guardian guardian);
}
