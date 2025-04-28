package com.hlp.api.domain.guardian.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;

@Getter
@RedisHash(value = "guardianCertificationCode@")
public class GuardianCertificationCode {

    private static final long CACHE_EXPIRE_SECOND = 60 * 5L;

    @Id
    @Indexed
    private String phoneNumber;
    private String certificationCode;

    @TimeToLive
    private Long expiration;

    public GuardianCertificationCode(String phoneNumber, String certificationCode) {
        this.phoneNumber = phoneNumber;
        this.certificationCode = certificationCode;
        this.expiration = CACHE_EXPIRE_SECOND;
    }

    public static GuardianCertificationCode of(String phoneNumber, String certificationCode) {
        return new GuardianCertificationCode(phoneNumber, certificationCode);
    }
}
