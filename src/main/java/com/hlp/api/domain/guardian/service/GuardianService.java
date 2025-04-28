package com.hlp.api.domain.guardian.service;

import static com.hlp.api.common.auth.validation.PasswordValidator.checkPasswordMatches;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlp.api.common.auth.JwtProvider;
import com.hlp.api.common.util.SmsUtil;
import com.hlp.api.domain.guardian.dto.request.GuardianChildrenRegisterRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianLoginRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianRegisterRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianVerificationRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianVerifySmsVerificationRequest;
import com.hlp.api.domain.guardian.dto.response.ChildrenResponse;
import com.hlp.api.domain.guardian.dto.response.GuardianLoginResponse;
import com.hlp.api.domain.guardian.dto.response.GuardianResponse;
import com.hlp.api.domain.guardian.exception.CertificationCodeNotEqualException;
import com.hlp.api.domain.guardian.model.Guardian;
import com.hlp.api.domain.guardian.model.GuardianCertificationCode;
import com.hlp.api.domain.guardian.model.GuardianChildrenMap;
import com.hlp.api.domain.guardian.repository.GuardianCertificationCodeRepository;
import com.hlp.api.domain.guardian.repository.GuardianChildrenMapRepository;
import com.hlp.api.domain.guardian.repository.GuardianRepository;
import com.hlp.api.domain.user.exception.UserLoginIdDuplicateException;
import com.hlp.api.domain.user.exception.UserPhoneNumberDuplicateException;
import com.hlp.api.domain.user.exception.UserWithDrawException;
import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuardianService {

    private final GuardianCertificationCodeRepository guardianCertificationCodeRepository;
    private final GuardianChildrenMapRepository guardianChildrenMapRepository;
    private final UserRepository childrenRepository;
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
        String phoneNum = request.phoneNumber().replaceAll("-","");

        guardianRepository.findByPhoneNumber(phoneNum)
            .ifPresent(guardian -> {
                throw new UserPhoneNumberDuplicateException("등록된 전화번호입니다");
            });

        Random random = new Random();

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

        guardianRepository.findByLoginId(request.loginId())
            .ifPresent(guardian -> {
                throw new UserLoginIdDuplicateException("등록된 아이디입니다");
            });

        Guardian guardian = request.toEntity(passwordEncoder.encode(request.password()));
        guardianRepository.save(guardian);
    }

    public GuardianResponse getGuardian(Integer guardianId) {
        Guardian guardian = guardianRepository.getById(guardianId);
        return GuardianResponse.of(guardian);
    }

    @Transactional
    public void verifySmsVerificationCode(GuardianVerifySmsVerificationRequest request) {
        GuardianCertificationCode byVerify = guardianCertificationCodeRepository.getByPhoneNumber(request.phoneNumber());
        if (!request.certificationCode().equals(byVerify.getCertificationCode())) {
            throw new CertificationCodeNotEqualException("인증번호가 일치하지 않습니다.");
        }
        guardianCertificationCodeRepository.delete(byVerify);
    }

    public List<ChildrenResponse> getChildren(Integer guardianId) {
        Guardian guardian = guardianRepository.getById(guardianId);
        List<GuardianChildrenMap> guardianChildrenMaps = guardianChildrenMapRepository.getByGuardianId(guardian.getId());

        return guardianChildrenMaps.stream()
            .map(guardianChildrenMap -> ChildrenResponse.of(guardianChildrenMap.getChildren()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void registerChildren(Integer guardianId, GuardianChildrenRegisterRequest request) {
        Guardian guardian = guardianRepository.getById(guardianId);
        User children = childrenRepository.getById(request.childrenId());
        guardianChildrenMapRepository.save(request.toEntity(children, guardian));
    }
}
