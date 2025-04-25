package com.hlp.api.domain.game.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hlp.api.common.config.FileStorageProperties;
import com.hlp.api.domain.game.dto.request.GameCreateRequest;
import com.hlp.api.domain.game.dto.response.GameResponse;
import com.hlp.api.domain.game.exception.DataFileSaveException;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.repository.GameRepository;
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

    @Transactional
    public GameResponse createGame(
        GameCreateRequest request, MultipartFile eegDatafile, MultipartFile eyeDatafile, Integer userId
    ) {
        User user = userRepository.getById(userId);
        Game game = gameRepository.save(request.toEntity(user));

        String path = String.format(fileStorageProperties.path(), System.getProperty("user.dir"), user.getId(), game.getId());

        try {
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File eegFile = new File(directory, Objects.requireNonNull(eegDatafile.getOriginalFilename()));
            File eyeFile = new File(directory, Objects.requireNonNull(eyeDatafile.getOriginalFilename()));

            eegDatafile.transferTo(eegFile);
            eyeDatafile.transferTo(eyeFile);

        } catch (IOException e) {
            throw new DataFileSaveException("생체 데이터 저장 과정에서 오류가 발생했습니다");
        }

        return GameResponse.of(game);
    }

    public List<GameResponse> getGames(Integer userId) {
        User user = userRepository.getById(userId);
        List<Game> games = gameRepository.findAllByUserId(user.getId());
        return games.stream().map(GameResponse::of).collect(Collectors.toList());
    }
}
