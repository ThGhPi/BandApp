package com.thghpi.bandapp.band_api.config.properties;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.password")
public class PasswordProperties {
    private final String regex;
    private final Long minLength;
    private final Long maxLength;
    private final Boolean requireUppercase;
    private final Boolean requireLowercase;
    private final Boolean requireDigit;
    private final Boolean requireSpecialCharacter;
}
