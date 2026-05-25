package com.thghpi.bandapp.band_api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(
    String secretKey,
    Long expirationTime
) {
    
}