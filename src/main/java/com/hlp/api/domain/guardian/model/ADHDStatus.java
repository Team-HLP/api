package com.hlp.api.domain.guardian.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ADHDStatus {
    NORMAL("정상"),
    CAUTION("주의"),
    DANGER("위험"),
    ;

    private final String description;
}
