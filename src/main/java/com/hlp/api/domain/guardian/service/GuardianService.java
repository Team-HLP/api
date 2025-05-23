package com.hlp.api.domain.guardian.service;

import static com.hlp.api.admin.game.model.EyeData.BasePupilSize;
import static com.hlp.api.admin.game.model.EyeData.PupilRecord;
import static com.hlp.api.common.auth.validation.PasswordValidator.checkPasswordMatches;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlp.api.admin.game.model.BehaviorData;
import com.hlp.api.admin.game.model.BioData;
import com.hlp.api.admin.game.model.EegData;
import com.hlp.api.admin.game.model.EyeData;
import com.hlp.api.admin.game.model.TBRConversionProperties;
import com.hlp.api.admin.game.model.TBRStandard;
import com.hlp.api.admin.game.util.BioDataReader;
import com.hlp.api.common.auth.JwtProvider;
import com.hlp.api.common.util.SmsUtil;
import com.hlp.api.domain.game.exception.DataFileSaveException;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.model.GameCategory;
import com.hlp.api.domain.game.model.MeteoriteDestruction;
import com.hlp.api.domain.game.model.MoleCatch;
import com.hlp.api.domain.game.repository.GameRepository;
import com.hlp.api.domain.game.repository.MeteoriteDestructionRepository;
import com.hlp.api.domain.game.repository.MoleCatchRepository;
import com.hlp.api.domain.guardian.dto.request.ChildrenRegisterVerifyRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianChildrenRegisterRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianLoginRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianRegisterRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianVerificationRequest;
import com.hlp.api.domain.guardian.dto.request.GuardianVerifySmsVerificationRequest;
import com.hlp.api.domain.guardian.dto.response.ChildADHDStatisticsResponse;
import com.hlp.api.domain.guardian.dto.response.ChildrenGameResponse;
import com.hlp.api.domain.guardian.dto.response.ChildrenResponse;
import com.hlp.api.domain.guardian.dto.response.GuardianLoginResponse;
import com.hlp.api.domain.guardian.dto.response.GuardianResponse;
import com.hlp.api.domain.guardian.exception.CertificationCodeNotEqualException;
import com.hlp.api.domain.guardian.exception.DuplicateRegisterChildren;
import com.hlp.api.domain.guardian.model.ADHDStatus;
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

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuardianService {

    private final TBRConversionProperties tbrConversionProperties;
    private final GuardianCertificationCodeRepository guardianCertificationCodeRepository;
    private final GuardianChildrenMapRepository guardianChildrenMapRepository;
    private final MeteoriteDestructionRepository meteoriteDestructionRepository;
    private final MoleCatchRepository moleCatchRepository;
    private final UserRepository childrenRepository;
    private final GuardianRepository guardianRepository;
    private final PasswordEncoder passwordEncoder;
    private final BioDataReader bioDataReader;
    private final JwtProvider jwtProvider;
    private final SmsUtil smsUtil;
    private final GameRepository gameRepository;

    private final List<TBRStandard> standards = new ArrayList<>();

    @PostConstruct
    public void init() {
        TBRConversionProperties.Minus minus = tbrConversionProperties.minus();
        standards.add(new TBRStandard(minus.score(), minus.standard().over(), minus.standard().blew()));

        TBRConversionProperties.Plus plus = tbrConversionProperties.plus();
        standards.add(new TBRStandard(plus.score(), plus.standard().over(), plus.standard().blew()));

        TBRConversionProperties.Zero zero = tbrConversionProperties.zero();
        standards.add(new TBRStandard(zero.score(), zero.standard().over(), zero.standard().blew()));
    }

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
        smsUtil.sendOne(phoneNum, certificationCode);

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

    public ChildrenResponse getChild(Integer childrenId, Integer guardianId) {
        GuardianChildrenMap guardianChildrenMap = guardianChildrenMapRepository.getByGuardianIdAndChildrenId(guardianId, childrenId);
        return ChildrenResponse.of(guardianChildrenMap.getChildren());
    }

    @Transactional
    public void registerChildren(Integer guardianId, GuardianChildrenRegisterRequest request) {
        Guardian guardian = guardianRepository.getById(guardianId);
        User children = childrenRepository.getByLoginId(request.childrenId());

        guardianChildrenMapRepository.findByGuardianIdAndChildrenId(guardian.getId(), children.getId())
                .ifPresent(guardianChildrenMap -> {
                    throw new DuplicateRegisterChildren("등록 된 자녀입니다.");
                });

        String phoneNumber = children.getPhoneNumber().replaceAll("-", "");
        Random random = new Random();
        String certificationCode = String.valueOf(random.nextInt(900000) + 100000);
        smsUtil.sendOne(phoneNumber, certificationCode);

        guardianCertificationCodeRepository.save(GuardianCertificationCode.of(phoneNumber, certificationCode));
    }

    @Transactional
    public void verifyChildrenRegister(Integer guardianId, ChildrenRegisterVerifyRequest request) {
        Guardian guardian = guardianRepository.getById(guardianId);
        User children = childrenRepository.getByLoginId(request.childrenId());

        guardianChildrenMapRepository.findByGuardianIdAndChildrenId(guardian.getId(), children.getId())
            .ifPresent(guardianChildrenMap -> {
                throw new DuplicateRegisterChildren("등록 된 자녀입니다.");
            });

        GuardianCertificationCode byVerify = guardianCertificationCodeRepository.getByPhoneNumber(children.getPhoneNumber());
        if (!request.certificationCode().equals(byVerify.getCertificationCode())) {
            throw new CertificationCodeNotEqualException("인증번호가 일치하지 않습니다.");
        }

        guardianCertificationCodeRepository.delete(byVerify);
        guardianChildrenMapRepository.save(GuardianChildrenMap.builder()
            .children(children)
            .guardian(guardian)
            .build());
    }

    public List<ChildrenResponse> getChildren(Integer guardianId) {
        Guardian guardian = guardianRepository.getById(guardianId);
        return guardianChildrenMapRepository.getByGuardianId(guardian.getId()).stream()
            .map(guardianChildrenMap -> ChildrenResponse.of(guardianChildrenMap.getChildren()))
            .toList();
    }

    public List<ChildrenGameResponse> getChildrenGames(Integer guardianId, Integer childrenId) {
        Guardian guardian = guardianRepository.getById(guardianId);
        User children = childrenRepository.getById(childrenId);
        guardianChildrenMapRepository.getByGuardianIdAndChildrenId(guardian.getId(), children.getId());
        List<Game> games = gameRepository.findAllByUserId(children.getId());

        List<ChildrenGameResponse> responses = new ArrayList<>();

        for (int index = 0; index < games.size(); index++) {
            Game game = games.get(index);
            String adhdStatus = getChildADHDStatistics(game.getId(), childrenId, guardianId).adhdStatus();
            responses.add(ChildrenGameResponse.of(game.getId(), index + 1, adhdStatus, game.getCreatedAt().toLocalDate()));
        }

        return responses;
    }

    public List<ChildADHDStatisticsResponse> getChildADHDStatistics(Integer childrenId, Integer guardianId) {
        List<Game> games = gameRepository.findAllByUserId(childrenId);
        List<ChildADHDStatisticsResponse> responses = new ArrayList<>();

        for (Game game : games) {
            ChildADHDStatisticsResponse response = getChildADHDStatistics(game.getId(), childrenId, guardianId);
            responses.add(response);
        }

        return responses;
    }

    // TODO. 캐싱 추가
    public ChildADHDStatisticsResponse getChildADHDStatistics(Integer gameId, Integer childrenId, Integer guardianId) {
        Guardian guardian = guardianRepository.getById(guardianId);
        User children = childrenRepository.getById(childrenId);
        Game game = gameRepository.getById(gameId);
        guardianChildrenMapRepository.getByGuardianIdAndChildrenId(guardian.getId(), children.getId());

        final int maxScore;
        if (game.getGameCategory() == GameCategory.METEORITE_DESTRUCTION) {
            MeteoriteDestruction meteoriteDestruction = meteoriteDestructionRepository.findByGameId(game.getId());
            Integer fuelCount = meteoriteDestruction.getFuelCount();
            Integer meteoriteCount = meteoriteDestruction.getMeteoriteCount();
            maxScore = 3 * (meteoriteCount + fuelCount);
        }
        else if (game.getGameCategory() == GameCategory.CATCH_MOLE) {
            MoleCatch moleCatch = moleCatchRepository.findByGameId(game.getId());
            Integer fuelCount = moleCatch.getFuelCount();
            Integer meteoriteCount = moleCatch.getMeteoriteCount();
            maxScore = 3 * (meteoriteCount + fuelCount);
        }
        else maxScore = 50;

        int impulseControlScore = 0;
        int concentrationScore = 0;

        BioData bioData = bioDataReader.readBioData(game.getId(), children.getId());

        List<EegData> eegData = bioData.eegData();

        EyeData eyeData = bioData.eyeData();
        BasePupilSize basePupilSize = eyeData.basePupilSize();
        List<PupilRecord> pupilRecords = eyeData.pupilRecords();

        List<BehaviorData> behaviorData = bioDataReader.readBehaviorData(game.getId(), children.getId());

        Integer eegDataSize = behaviorData.size();
        Integer pupilRecordSize = pupilRecords.size();

        for (int index = 0; index < behaviorData.size(); index++) {
            if (index >= eegDataSize || index >= pupilRecordSize) {
                break;
            }
            if (!eegData.get(index).isValid()) {
                continue;
            }

            BehaviorData behavior = behaviorData.get(index);
            if (isGazeStatus(behavior.status())) continue;

            try {
                impulseControlScore += calculateImpulseScore(behavior, eegData.get(index));
                concentrationScore += calculateConcentrationScore(behavior, pupilRecords.get(index), basePupilSize);
            } catch (IllegalArgumentException e) {
                throw new DataFileSaveException("생체 데이터 읽기 과정에서 오류가 발생했습니다");
            }
        }

        double weight = calculateBlinkWeight(eyeData.blinkEyeCount());
        impulseControlScore = Math.max(impulseControlScore, 0);
        concentrationScore = Math.max(concentrationScore, 0);
        double convertedImpulse = convertScore(impulseControlScore, maxScore);
        double convertedConcentration = convertScore(concentrationScore, weight, maxScore);
        double totalScore = convertedImpulse + convertedConcentration;

        ADHDStatus result = evaluateStatus(totalScore);

        return ChildADHDStatisticsResponse.from(convertedImpulse, convertedConcentration, result);
    }

    private boolean isGazeStatus(String status) {
        return "GAZE".equals(status) || "NOT_GAZE".equals(status);
    }

    /**
     * 집중력 점수 계산
     * - 운석
     *     - 유저 파괴 o
     *         - TBR에 상관없이 +3
     *     - 파괴 x
     *         - TBR < 4 : 0
     *         - 4 ≤ TBR ≤ 6 : -1
     *         - TBR > 6 : -2
     * - 연료
     *     - 파괴 o
     *         - TBR에 상관없이 -1
     *     - 파괴 x
     *         - TBR < 4 : +3
     *         - 4 ≤ TBR ≤ 6 : +2
     *         - TBR > 6 : +1
     */
    private int calculateImpulseScore(BehaviorData data, EegData eeg) {
        String status = data.status();
        String object = data.ObjectName();
        Integer tbr = eeg.getTBR();

        if ("USER_DESTROY".equals(status)) {
            if ("Meteorite".equals(object)) {
                return 3;
            }
            if ("Fuel".equals(object)) {
                return -1;
            }
        } else if ("AUTO_DESTROY".equals(status)) {
            if ("Meteorite".equals(object)) {
                if (tbr < 4) return 0;
                else if (tbr <= 6) return -1;
                else return -2;
            }
            if ("Fuel".equals(object)) {
                if (tbr < 4) return 3;
                else if (tbr <= 6) return 2;
                else return 1;
            }
        }

        throw new IllegalArgumentException("Invalid behavior status or object");
    }

    /**
     * 집중력 점수 계산
     * - 운석
     *     - 파괴 o
     *         - 동공 크기 상관 없이 +3
     *     - 파괴 x
     *         - 동공 크기가 크면 -1
     *         - 동공 크기가 낮으면 -2
     * - 연료
     *     - 파괴 o
     *         - 동공 크기가 크면 +1
     *         - 동공 크기가 낮으면 -2
     *     - 파괴 x
     *         - 동공 크기가 크면 +3
     *         - 동공 크기가 낮으면 +1
     */
    private int calculateConcentrationScore(BehaviorData data, PupilRecord pupil, BasePupilSize base) {
        String status = data.status();
        String object = data.ObjectName();
        boolean isConcentrated = isPupilDilated(pupil, base);

        if ("USER_DESTROY".equals(status)) {
            if ("Meteorite".equals(object)) {
                return 3;
            }
            if ("Fuel".equals(object)) {
                if (isConcentrated) return 1;
                else return -2;
            }
        } else if ("AUTO_DESTROY".equals(status)) {
            if ("Meteorite".equals(object)) {
                if (isConcentrated) return -1;
                else return -2;
            }
            if ("Fuel".equals(object)) {
                if (isConcentrated) return 3;
                else return 1;
            }
        }

        throw new IllegalArgumentException("Invalid behavior status or object");
    }

    private boolean isPupilDilated(PupilRecord record, BasePupilSize base) {
        double leftThreshold = base.left() * 0.9;
        double rightThreshold = base.right() * 0.9;
        return record.pupilSize().left() >= leftThreshold && record.pupilSize().right() >= rightThreshold;
    }

    private double calculateBlinkWeight(int count) {
        if (count <= 15) return 1.0;
        if (count <= 19) return 0.9;
        if (count <= 21) return 0.8;
        if (count <= 26) return 0.7;
        return 0.6;
    }

    private double convertScore(int rawScore, double weight, int maxScore) {
        return (rawScore * weight) / maxScore * 27.0;
    }

    private double convertScore(int rawScore, int maxScore) {
        return (rawScore * 27.0) / maxScore;
    }

    private ADHDStatus evaluateStatus(double score) {
        if (score <= 17.0) return ADHDStatus.DANGER;
        if (score <= 35.0) return ADHDStatus.CAUTION;
        return ADHDStatus.NORMAL;
    }
}
