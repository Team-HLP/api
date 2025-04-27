package com.hlp.api.admin.game.model;

import java.util.List;

public record EyeData(
    BasePupilSize basePupilSize,
    List<PupilRecord> pupilRecords,
    Integer totalBlinkEyeCount
) {
    public record BasePupilSize(
        Double left,
        Double right
    ) {

    }

    public record PupilRecord(
        Double timeStamp,
        PupilSize pupilSize
    ) {

    }

    public record PupilSize(
        Double left,
        Double right
    ) {

    }
}
