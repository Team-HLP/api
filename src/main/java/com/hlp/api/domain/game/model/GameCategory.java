package com.hlp.api.domain.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameCategory {
    METEORITE_DESTRUCTION("쾅! 운석 요격전"),
    CATCH_MOLE("외계인을 물리쳐라"),
    ;

    private final String displayName;
}
