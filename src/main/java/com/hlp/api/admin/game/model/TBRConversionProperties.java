package com.hlp.api.admin.game.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tbr")
public record TBRConversionProperties(
    Plus plus,
    Zero zero,
    Minus minus,
    Integer totalScore,
    Integer conversionScore
) {
    public record Plus(
        Integer score,
        Standard standard
    ) {

    }

    public record Minus(
        Integer score,
        Standard standard
    ) {

    }

    public record Zero(
        Integer score,
        Standard standard
    ) {

    }

    public record Standard(
        Integer over,
        Integer blew
    ) {

    }
}
