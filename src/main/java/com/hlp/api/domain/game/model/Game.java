package com.hlp.api.domain.game.model;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.hlp.api.common.model.BaseEntity;
import com.hlp.api.domain.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(STRING)
    @Column(name = "result", nullable = false)
    private Result result;

    @NotNull
    @Enumerated(STRING)
    @Column(name = "game_category", nullable = false)
    private GameCategory gameCategory;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @NotNull
    @Column(name = "blink_eye_count", nullable = false)
    private Integer blinkEyeCount;

    @NotNull
    @Column(name = "avg_left_eye_pupil_size", nullable = false)
    private Float avgLeftEyePupilSize;

    @NotNull
    @Column(name = "avg_right_eye_pupil_size", nullable = false)
    private Float avgRightEyePupilSize;

    @Builder
    private Game(
        Integer id,
        Result result,
        GameCategory gameCategory,
        User user,
        Boolean isDeleted,
        Integer blinkEyeCount,
        Float avgLeftEyePupilSize,
        Float avgRightEyePupilSize
    ) {
        this.id = id;
        this.result = result;
        this.gameCategory = gameCategory;
        this.user = user;
        this.isDeleted = isDeleted;
        this.blinkEyeCount = blinkEyeCount;
        this.avgLeftEyePupilSize = avgLeftEyePupilSize;
        this.avgRightEyePupilSize = avgRightEyePupilSize;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
