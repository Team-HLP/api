package com.hlp.api.admin.game.util;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlp.api.admin.game.model.BehaviorData;
import com.hlp.api.admin.game.model.BioData;
import com.hlp.api.admin.game.model.EegData;
import com.hlp.api.admin.game.model.EyeData;
import com.hlp.api.common.config.FileStorageProperties;
import com.hlp.api.domain.game.exception.DataFileSaveException;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BioDataReader {

    private final FileStorageProperties fileStorageProperties;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper.setPropertyNamingStrategy(SNAKE_CASE);
    }

    public BioData readBioData(Integer gameId, Integer userId) {
        String path = String.format(fileStorageProperties.path(), System.getProperty("user.dir"), userId, gameId);

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

        return new BioData(eyeData, eegData);
    }

    public List<BehaviorData> readBehaviorData(Integer gameId, Integer userId) {
        String path = String.format(fileStorageProperties.path(), System.getProperty("user.dir"), userId, gameId);

        File behaviorDataFilePath = new File(path, "behavior_data.json");

        List<BehaviorData> behaviorData;

        try {
            behaviorData = objectMapper.readValue(
                behaviorDataFilePath,
                new TypeReference<>() {
                }
            );
        } catch (IOException e) {
            throw new DataFileSaveException("생체 데이터 읽기 과정에서 오류가 발생했습니다");
        }

        return behaviorData;
    }
}
