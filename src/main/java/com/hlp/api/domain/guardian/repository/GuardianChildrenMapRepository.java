package com.hlp.api.domain.guardian.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.hlp.api.domain.guardian.exception.NotMatchGuardianChild;
import com.hlp.api.domain.guardian.model.GuardianChildrenMap;

public interface GuardianChildrenMapRepository extends Repository<GuardianChildrenMap, Integer> {

    List<GuardianChildrenMap> getByGuardianId(Integer guardianId);

    Optional<GuardianChildrenMap> findByGuardianIdAndChildrenId(Integer guardianId, Integer childId);

    void save(GuardianChildrenMap guardianChildrenMap);

    default GuardianChildrenMap getByGuardianIdAndChildrenId(Integer guardianId, Integer childId) {
        return findByGuardianIdAndChildrenId(guardianId, childId)
            .orElseThrow(() -> new NotMatchGuardianChild("등록된 자녀가 아닙니다."));
    }
}
