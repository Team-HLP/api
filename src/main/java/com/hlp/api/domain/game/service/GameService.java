package com.hlp.api.domain.game.service;

import static com.hlp.api.domain.game.model.GameCategory.METEORITE_DESTRUCTION;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hlp.api.common.config.FileStorageProperties;
import com.hlp.api.domain.game.dto.request.MeteoriteCreateRequest;
import com.hlp.api.domain.game.dto.request.MoleCreateRequest;
import com.hlp.api.domain.game.dto.response.MeteoriteDestructionResponse;
import com.hlp.api.domain.game.exception.DataFileSaveException;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.model.GameCategory;
import com.hlp.api.domain.game.model.MeteoriteDestruction;
import com.hlp.api.domain.game.repository.GameRepository;
import com.hlp.api.domain.game.repository.MeteoriteDestructionRepository;
import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final FileStorageProperties fileStorageProperties;
    private final MeteoriteDestructionRepository meteoriteDestructionRepository;

    @Transactional
    public void crateMeteorite(
        // MeteoriteCreateRequest request, MultipartFile eegDataFile, MultipartFile eyeDatafile, Integer userId
        MeteoriteCreateRequest request, Integer userId
    ) {
        User user = userRepository.getById(userId);
        Game game = gameRepository.save(request.toGame(user));
        meteoriteDestructionRepository.save(request.toMeteoriteDestruction(game));

        // String path = String.format(fileStorageProperties.path(), System.getProperty("user.dir"), user.getId(), game.getId());
        // saveJsonFile(eegDataFile, path);
        // saveJsonFile(eyeDatafile, path);
    }

    @Transactional
    public void crateMole(
        MoleCreateRequest request, MultipartFile eegDataFile, MultipartFile eyeDatafile, Integer userId
    ) {

    }

    private void saveJsonFile(MultipartFile jsonFile, String path) {
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File eegFile = new File(directory, Objects.requireNonNull(jsonFile.getOriginalFilename()));

            jsonFile.transferTo(eegFile);

        } catch (IOException e) {
            throw new DataFileSaveException("생체 데이터 저장 과정에서 오류가 발생했습니다");
        }
    }

    public List<Object> getGames(Integer userId, GameCategory gameCategory) {
        User user = userRepository.getById(userId);

        List<Game> games = gameRepository.findAllByUserIdAndGameCategory(user.getId(), gameCategory);

        if (gameCategory == METEORITE_DESTRUCTION) {
            return games.stream()
                .map(game -> {
                    MeteoriteDestruction meteoriteDestruction = meteoriteDestructionRepository.findByGameId(
                        game.getId());
                    return MeteoriteDestructionResponse.of(game, meteoriteDestruction);
                })
                .collect(Collectors.toList());
        }
        else {
            return Collections.emptyList();
        }
    }
}
