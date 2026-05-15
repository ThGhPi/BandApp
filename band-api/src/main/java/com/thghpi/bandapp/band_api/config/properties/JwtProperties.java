package com.thghpi.bandapp.band_api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    private final String secretKey;
    private final Long expirationTime;   
}