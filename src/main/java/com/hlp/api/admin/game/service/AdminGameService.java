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
import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminGameService {

    private final TBRConversionProperties tbrConversionProperties;
    private final AdminGameRepository adminGameRepository;
    private final UserRepository userRepository;
    private final BioDataReader bioDataReader;

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

        for (EegData eegData : eegDatas) {
            Double tbr = eegData.theta() / eegData.beta();

            for (TBRStandard standard : standards) {
                if (standard.isBetweenTBR(tbr)) {
                    totalScore += standard.score();
                    break;
                }
            }
        }

        return (double)((totalScore / (eegDatas.size() * tbrConversionProperties.totalScore())) * tbrConversionProperties.conversionScore());
    }
}
