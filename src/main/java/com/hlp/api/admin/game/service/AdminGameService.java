package com.hlp.api.admin.game.service;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;
import static com.hlp.api.domain.game.model.Result.SUCCESS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlp.api.admin.game.dto.response.AdminGameDetailResponse;
import com.hlp.api.admin.game.dto.response.AdminGameResponse;
import com.hlp.api.admin.game.dto.response.AdminGameStatisticsResponse;
import com.hlp.api.admin.game.model.BehaviorData;
import com.hlp.api.admin.game.model.BioData;
import com.hlp.api.admin.game.model.EegData;
import com.hlp.api.admin.game.model.EyeData;
import com.hlp.api.admin.game.model.TBRConversionProperties;
import com.hlp.api.admin.game.model.TBRStandard;
import com.hlp.api.admin.game.repository.AdminGameRepository;
import com.hlp.api.admin.game.util.BioDataReader;
import com.hlp.api.common.config.FileStorageProperties;
import com.hlp.api.domain.game.exception.DataFileSaveException;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.model.GameCategory;
import com.hlp.api.domain.game.model.MeteoriteDestruction;
import com.hlp.api.domain.game.model.MoleCatch;
import com.hlp.api.domain.game.repository.GameRepository;
import com.hlp.api.domain.game.repository.MeteoriteDestructionRepository;
import com.hlp.api.domain.game.repository.MoleCatchRepository;
import com.hlp.api.domain.guardian.dto.response.ChildADHDStatisticsResponse;
import com.hlp.api.domain.guardian.dto.response.ChildrenGameResponse;
import com.hlp.api.domain.guardian.model.ADHDStatus;
import com.hlp.api.domain.guardian.model.Guardian;
import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminGameService {

    private final TBRConversionProperties tbrConversionProperties;
    private final MeteoriteDestructionRepository meteoriteDestructionRepository;
    private final MoleCatchRepository moleCatchRepository;
    private final AdminGameRepository adminGameRepository;
    private final GameStatisticsRedisService gameStatisticsRedisService;
    private final ChildAdhdStatisticsRedisService childAdhdStatisticsRedisService;
    private final UserRepository userRepository;
    private final BioDataReader bioDataReader;

    private final List<TBRStandard> standards = new ArrayList<>();
    private final GameRepository gameRepository;

    @PostConstruct
    public void init() {
        TBRConversionProperties.Minus minus = tbrConversionProperties.minus();
        standards.add(new TBRStandard(minus.score(), minus.standard().over(), minus.standard().blew()));

        TBRConversionProperties.Plus plus = tbrConversionProperties.plus();
        standards.add(new TBRStandard(plus.score(), plus.standard().over(), plus.standard().blew()));

        TBRConversionProperties.Zero zero = tbrConversionProperties.zero();
        standards.add(new TBRStandard(zero.score(), zero.standard().over(), zero.standard().blew()));
    }

    public List<AdminGameResponse> getGames(Integer userId) {
        User user = userRepository.getById(userId);
        List<Game> games = adminGameRepository.findAllByUserId(user.getId());
        return games.stream().map(AdminGameResponse::of).collect(Collectors.toList());
    }

    public AdminGameDetailResponse getGame(Integer userId, Integer gameId) {
        User user = userRepository.getById(userId);
        Game game = adminGameRepository.getByIdAndUserId(gameId, user.getId());

        BioData bioData = bioDataReader.readBioData(gameId, user.getId());

        return AdminGameDetailResponse.of(bioData.eyeData(), bioData.eegData(), game.getCreatedAt());
    }

    public List<AdminGameStatisticsResponse> getGameStatistics(Integer userId) {
        User user = userRepository.getById(userId);
        List<Game> games = adminGameRepository.findAllByUserId(user.getId()).stream()
            .filter(game -> game.getResult().equals(SUCCESS))
            .toList();

        List<AdminGameStatisticsResponse> responses = new ArrayList<>();

        for (Game game : games) {
            BioData bioData = bioDataReader.readBioData(game.getId(), user.getId());
            responses.add(AdminGameStatisticsResponse.of(
                bioData.eyeData().blinkEyeCount(),
                bioData.eyeData().basePupilSize().left(),
                bioData.eyeData().basePupilSize().right(),
                bioData.eyeData().belowBaseLeftPupilCount(),
                bioData.eyeData().belowBaseRightPupilCount(),
                calcTBRConversionScore(bioData.eegData()),
                game.getCreatedAt()
            ));
        }

        return responses;
    }

    private Double calcTBRConversionScore(List<EegData> eegDatas) {
        Integer totalScore = 0;
        int size = eegDatas.size();

        for (EegData eegData : eegDatas) {
            if (eegData.theta() < 0.0001 || eegData.beta() < 0.0001) {
                size--;
                continue;
            }
            Double tbr = eegData.theta() / eegData.beta();

            for (TBRStandard standard : standards) {
                if (standard.isBetweenTBR(tbr)) {
                    totalScore += standard.score();
                    break;
                }
            }
        }

        return (double)totalScore / (size * tbrConversionProperties.totalScore()) * tbrConversionProperties.conversionScore();
    }

    public List<ChildADHDStatisticsResponse> getChildrenGames(Integer childrenId) {
        User children = userRepository.getById(childrenId);
        List<Game> games = gameRepository.findAllByUserId(children.getId());

        List<ChildADHDStatisticsResponse> responses = new ArrayList<>();

        for (Game game : games) {
            ChildADHDStatisticsResponse response = childAdhdStatisticsRedisService.get(
                String.valueOf(game.getId()));

            if (response != null) {
                responses.add(response);
            }
            else {
                responses.add(getChildADHDStatistics(game.getId(), childrenId));
            }
        }

        return responses;
    }

    public ChildADHDStatisticsResponse getChildADHDStatistics(Integer gameId, Integer childrenId) {
        User children = userRepository.getById(childrenId);
        Game game = gameRepository.getById(gameId);

        ChildADHDStatisticsResponse response = childAdhdStatisticsRedisService.get(String.valueOf(game.getId()));
        if (response != null) {
            return response;
        }

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
        EyeData.BasePupilSize basePupilSize = eyeData.basePupilSize();
        List<EyeData.PupilRecord> pupilRecords = eyeData.pupilRecords();

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
    private int calculateConcentrationScore(BehaviorData data, EyeData.PupilRecord pupil, EyeData.BasePupilSize base) {
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

    private boolean isPupilDilated(EyeData.PupilRecord record, EyeData.BasePupilSize base) {
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
