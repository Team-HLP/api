package com.hlp.api.domain.game.model;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.hlp.api.common.model.BaseEntity;
import com.hlp.api.domain.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @NotNull
    @Column(name = "blink_eye_count", nullable = false)
    private Integer blinkEyeCount;

    @NotNull
    @Column(name = "base_left_pupil_size", nullable = false)
    private Double baseLeftPupilSize;

    @NotNull
    @Column(name = "below_base_left_pupil_count", nullable = false)
    private Integer belowBaseLeftPupilCount;

    @NotNull
    @Column(name = "base_right_pupil_size", nullable = false)
    private Double baseRightPupilSize;

    @NotNull
    @Column(name = "below_base_right_pupil_count", nullable = false)
    private Integer belowBaseRightPupilCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    private Game(
        Integer id,
        Result result,
        GameCategory gameCategory,
        Integer blinkEyeCount,
        Double baseLeftPupilSize,
        Integer belowBaseLeftPupilCount,
        Double baseRightPupilSize,
        Integer belowBaseRightPupilCount,
        User user,
        Boolean isDeleted
    ) {
        this.id = id;
        this.result = result;
        this.gameCategory = gameCategory;
        this.blinkEyeCount = blinkEyeCount;
        this.baseLeftPupilSize = baseLeftPupilSize;
        this.belowBaseLeftPupilCount = belowBaseLeftPupilCount;
        this.baseRightPupilSize = baseRightPupilSize;
        this.belowBaseRightPupilCount = belowBaseRightPupilCount;
        this.user = user;
        this.isDeleted = isDeleted;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
