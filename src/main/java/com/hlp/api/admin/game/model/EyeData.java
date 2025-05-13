package com.hlp.api.admin.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record EyeData(
    @JsonProperty("base_pupil_size") BasePupilSize basePupilSize,
    @JsonProperty("pupil_records") List<PupilRecord> pupilRecords,
    @JsonProperty("blink_eye_count") Integer blinkEyeCount
) {
    private static final Double TOLERANCE = 0.1;

    public record BasePupilSize(
        @JsonProperty("left") Double left,
        @JsonProperty("right") Double right
    ) {}

    public record PupilRecord(
        @JsonProperty("time_stamp") Double timeStamp,
        @JsonProperty("pupil_size") PupilSize pupilSize
    ) {}

    public record PupilSize(
        @JsonProperty("left") Double left,
        @JsonProperty("right") Double right
    ) {}

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
