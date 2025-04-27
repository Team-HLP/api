package com.hlp.api.domain.guardian.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.guardian.exception.VerifyNotFoundException;
import com.hlp.api.domain.guardian.model.GuardianCertificationCode;

public interface GuardianCertificationCodeRepository extends Repository<GuardianCertificationCode, String> {

    void save(GuardianCertificationCode guardianCertificationCode);

    Optional<GuardianCertificationCode> findByPhoneNumber(String phoneNumber);

    default GuardianCertificationCode getByVerify(String phoneNumber) {
        return findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new VerifyNotFoundException("인증번호가 존재하지 않습니다."));
    }

    void remove(GuardianCertificationCode guardianCertificationCode);
}
