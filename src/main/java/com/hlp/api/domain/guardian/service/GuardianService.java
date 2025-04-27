package com.hlp.api.domain.guardian.service;

import static com.hlp.api.common.auth.validation.PasswordValidator.checkPasswordMatches;

import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlp.api.common.auth.JwtProvider;
import com.hlp.api.common.util.SmsUtil;
import com.hlp.api.domain.guardian.dto.request.GuardianLoginRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianRegisterRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianVerificationRequest;
import com.hlp.api.domain.guardian.dto.response.GuardianLoginResponse;
import com.hlp.api.domain.guardian.dto.response.GuardianResponse;
import com.hlp.api.domain.guardian.model.Guardian;
import com.hlp.api.domain.guardian.model.GuardianCertificationCode;
import com.hlp.api.domain.guardian.repository.GuardianCertificationCodeRepository;
import com.hlp.api.domain.guardian.repository.GuardianRepository;
import com.hlp.api.domain.user.exception.UserPhoneNumberDuplicateException;
import com.hlp.api.domain.user.exception.UserWithDrawException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuardianService {

    private final GuardianCertificationCodeRepository guardianCertificationCodeRepository;
    private final GuardianRepository guardianRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final SmsUtil smsUtil;

    @Transactional
    public GuardianLoginResponse login(GuardianLoginRequest request) {
        Guardian guardian = guardianRepository.getByLoginId(request.loginId());
        if (guardian.getIsDeleted()) {
            throw new UserWithDrawException("탈퇴한 유저입니다.");
        }
        checkPasswordMatches(passwordEncoder, request.password(), guardian.getPassword());
        String accessToken = jwtProvider.createToken(guardian);
        return GuardianLoginResponse.of(accessToken);
    }

    @Transactional
    public void sendCertificationCode(GuardianVerificationRequest request) {
        Random random = new Random();

        String phoneNum = request.phoneNumber().replaceAll("-","");
        if (guardianRepository.findByLoginId(phoneNum).isPresent()) {
            throw new UserPhoneNumberDuplicateException("등록된 전화번호입니다");
        }

        String certificationCode = String.valueOf(random.nextInt(900000) + 100000);
        // smsUtil.sendOne(phoneNum, certificationCode);

        guardianCertificationCodeRepository.save(GuardianCertificationCode.of(phoneNum, certificationCode));
    }

    @Transactional
    public void register(GuardianRegisterRequest request) {
        guardianRepository.findByPhoneNumber(request.phoneNumber())
            .ifPresent(guardian -> {
                throw new UserPhoneNumberDuplicateException("등록된 전화번호입니다");
            });

        Guardian guardian = request.toEntity(passwordEncoder.encode(request.password()));
        guardianRepository.save(guardian);
    }

    public GuardianResponse getGuardian(Integer guardianId) {
        Guardian guardian = guardianRepository.getById(guardianId);
        return GuardianResponse.of(guardian);
    }
}
