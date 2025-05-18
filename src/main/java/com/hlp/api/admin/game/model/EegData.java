package com.hlp.api.admin.game.model;

public record EegData(
    Double timeStamp,
    Double delta,
    Double theta,
    Double alpha,
    Double beta,
    Double gamma
) {
    public Integer getTBR() {
        if (!isValid()) return null;
        return (int)(theta / beta);
    }

    public Boolean isValid() {
        return theta != null && beta != null && !theta.isNaN() && !beta.isNaN() && beta != 0.0;
    }
}
