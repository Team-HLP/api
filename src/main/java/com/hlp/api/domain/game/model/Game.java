package com.hlp.api.domain.game.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.hlp.api.common.model.BaseEntity;
import com.hlp.api.domain.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "games")
@NoArgsConstructor(access = PROTECTED)
public class Game extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "meteorite_broken_count", nullable = false)
    private Integer meteoriteBrokenCount;

    @NotNull
    @Column(name = "blink_eye_count", nullable = false)
    private Integer blinkEyeCount;

    @NotNull
    @Column(name = "avg_left_eye_pupil_size", nullable = false)
    private Float avgLeftEyePupilSize;

    @NotNull
    @Column(name = "avg_right_eye_pupil_size", nullable = false)
    private Float avgRightEyePupilSize;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Game(
        Integer id,
        Integer meteoriteBrokenCount,
        Integer blinkEyeCount,
        Float avgLeftEyePupilSize,
        Float avgRightEyePupilSize,
        User user
    ) {
        this.id = id;
        this.meteoriteBrokenCount = meteoriteBrokenCount;
        this.blinkEyeCount = blinkEyeCount;
        this.avgLeftEyePupilSize = avgLeftEyePupilSize;
        this.avgRightEyePupilSize = avgRightEyePupilSize;
        this.user = user;
    }
}
