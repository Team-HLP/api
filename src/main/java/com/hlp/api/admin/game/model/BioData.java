package com.hlp.api.admin.game.model;

import java.util.List;

public record BioData(
    EyeData eyeData,
    List<EegData> eegData
) {

}
