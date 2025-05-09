package com.hlp.api.domain.game.repository;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.game.model.MoleCatch;

public interface MoleCatchRepository extends Repository<MoleCatch, Integer> {
    void save(MoleCatch moleCatch);
}
