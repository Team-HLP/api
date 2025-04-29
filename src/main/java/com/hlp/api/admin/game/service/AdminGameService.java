package com.hlp.api.admin.game.service;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlp.api.admin.game.dto.response.AdminGameDetailResponse;
import com.hlp.api.admin.game.dto.response.AdminGameResponse;
import com.hlp.api.admin.game.model.EegData;
import com.hlp.api.admin.game.model.EyeData;
import com.hlp.api.admin.game.repository.AdminGameRepository;
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

    private final FileStorageProperties fileStorageProperties;
    private final AdminGameRepository adminGameRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper.setPropertyNamingStrategy(SNAKE_CASE);
    }

    public List<AdminGameResponse> getGames(Integer userId) {
        User user = userRepository.getById(userId);
        List<Game> games = adminGameRepository.findAllByUserId(user.getId());
        return games.stream().map(AdminGameResponse::of).collect(Collectors.toList());
    }

    public AdminGameDetailResponse getGame(Integer userId, Integer gameId) {
        User user = userRepository.getById(userId);
        Game game = adminGameRepository.getByIdAndUserId(gameId, user.getId());

        String path = String.format(fileStorageProperties.path(), System.getProperty("user.dir"), user.getId(), game.getId());

        File eyeDataFilePath = new File(path, "eye_data.json");
        File eegDataFilePath = new File(path, "eeg_data.json");
        EyeData eyeData;
        List<EegData> eegData;

        try {
            eyeData = objectMapper.readValue(eyeDataFilePath, EyeData.class);
            eegData = objectMapper.readValue(
                eegDataFilePath,
                new TypeReference<>() {
                }
            );
        } catch (IOException e) {
            throw new DataFileSaveException("생체 데이터 읽기 과정에서 오류가 발생했습니다");
        }

        return AdminGameDetailResponse.of(eyeData, eegData);
    }
}
