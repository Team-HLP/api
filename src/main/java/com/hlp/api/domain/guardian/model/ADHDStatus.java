package com.hlp.api.domain.guardian.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ADHDStatus {
    NORMAL("정상", 36, null),
    CAUTION("주의", 35, 18),
    DANGER("위험", 17, 0),
    ;

    private final String description;
    private final Integer low;
    private final Integer high;
}
