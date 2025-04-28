package com.hlp.api.domain.game.repository;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.game.model.MeteoriteDestruction;

public interface MeteoriteDestructionRepository extends Repository<MeteoriteDestruction, Integer> {
    MeteoriteDestruction findByGameId(Integer gameId);

    void save(MeteoriteDestruction meteoriteDestruction);
}
