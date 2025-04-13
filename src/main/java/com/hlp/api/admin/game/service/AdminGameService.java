package com.hlp.api.admin.game.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlp.api.admin.game.dto.response.AdminGameResponse;
import com.hlp.api.admin.game.repository.AdminGameRepository;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.user.model.User;
import com.hlp.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminGameService {

    private final AdminGameRepository adminGameRepository;
    private final UserRepository userRepository;

    public List<AdminGameResponse> getGames(Integer userId) {
        User user = userRepository.getById(userId);
        List<Game> games = adminGameRepository.getByUserId(userId);
        return games.stream().map(AdminGameResponse::of).collect(Collectors.toList());
    }
}
