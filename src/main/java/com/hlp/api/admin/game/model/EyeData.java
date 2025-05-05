package com.hlp.api.admin.game.model;

import java.util.List;

public record EyeData(
    BasePupilSize basePupilSize,
    List<PupilRecord> pupilRecords,
    Integer blinkEyeCount
) {
    private static final Double TOLERANCE = 0.1;

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

    public Integer belowBaseLeftPupilCount() {
        int count = 0;
        for (PupilRecord record : pupilRecords) {
            if (record.pupilSize.left != null && basePupilSize.left != null) {
                if (record.pupilSize.left < basePupilSize.left - (basePupilSize.left * TOLERANCE)) {
                    count++;
                }
            }
        }
        return count;
    }

    public Integer belowBaseRightPupilCount() {
        int count = 0;
        for (PupilRecord record : pupilRecords) {
            if (record.pupilSize.right != null && basePupilSize.right != null) {
                if (record.pupilSize.right < basePupilSize.right - (basePupilSize.right * TOLERANCE)) {
                    count++;
                }
            }
        }
        return count;
    }
}
