package com.hlp.api.domain.game.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "meteroite_destruction")
@NoArgsConstructor(access = PROTECTED)
public class MeteoriteDestruction {

    @Id
    @Column(name = "game_id", nullable = false)
    private Integer id;

    @OneToOne
    @MapsId
    private Game game;

    @NotNull
    @Column(name = "score", nullable = false)
    private Integer score;

    @NotNull
    @Column(name = "hp", nullable = false)
    private Integer hp;

    @NotNull
    @Column(name = "meteorite_broken_count", nullable = false)
    private Integer meteoriteBrokenCount;

    @NotNull
    @Column(name = "meteorite_count", nullable = false)
    private Integer meteoriteCount;

    @NotNull
    @Column(name = "fuel_count", nullable = false)
    private Integer fuelCount;

    @Builder
    private MeteoriteDestruction(
        Integer id,
        Game game,
        Integer score,
        Integer hp,
        Integer meteoriteBrokenCount,
        Integer meteoriteCount,
        Integer fuelCount
    ) {
        this.id = id;
        this.game = game;
        this.score = score;
        this.hp = hp;
        this.meteoriteBrokenCount = meteoriteBrokenCount;
        this.meteoriteCount = meteoriteCount;
        this.fuelCount = fuelCount;
    }
}
