package com.hlp.api.admin.game.model;

public record EegData(
    Double timeStamp,
    Double delta,
    Double theta,
    Double alpha,
    Double beta,
    Double gamma
) {
    public Double getTBR() {
        return theta / beta;
    }
}
