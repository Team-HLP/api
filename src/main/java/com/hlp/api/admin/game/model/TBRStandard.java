package com.hlp.api.admin.game.model;

public record TBRStandard(
    Integer score,
    Integer over,
    Integer blew
) {
    public boolean isBetweenTBR(Double tbr) {
        return over <= tbr && tbr <= blew;
    }
}
