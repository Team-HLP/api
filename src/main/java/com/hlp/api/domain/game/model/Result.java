package com.hlp.api.domain.game.model;

import lombok.Getter;

@Getter
public enum Result {
    SUCCESS("성공"),
    FAIL("실패"),
    ;

    private final String description;

    Result(String description) {
        this.description = description;
    }
}
