package com.hlp.api.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file.upload")
public record FileStorageProperties(
    String path
) {

}
