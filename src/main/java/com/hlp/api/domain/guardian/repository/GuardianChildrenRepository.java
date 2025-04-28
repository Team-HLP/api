package com.hlp.api.domain.guardian.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.guardian.model.GuardianChildrenMap;

public interface GuardianChildrenRepository extends Repository<GuardianChildrenMap, Integer> {

    List<GuardianChildrenMap> getByGuardianId(Integer guardianId);
}
