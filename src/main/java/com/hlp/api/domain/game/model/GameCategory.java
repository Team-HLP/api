package com.hlp.api.domain.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameCategory {
    METEORITE_DESTRUCTION("운석 부수기"),
    CATCH_MOLE("두더지 잡기"),
    ;

    private final String displayName;
}
