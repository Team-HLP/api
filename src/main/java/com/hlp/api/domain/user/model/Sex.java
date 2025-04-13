package com.hlp.api.domain.user.model;

import lombok.Getter;

@Getter
public enum Sex {
    MAN("남"),
    WOMAN("여");

    private final String description;

    Sex(String description) {
        this.description = description;
    }
}
