package com.thghpi.bandapp.band_api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.password")
public record PasswordProperties(
    String regex,
    Long minLength,
    Long maxLength,
    Boolean requireUppercase,
    Boolean requireLowercase,
    Boolean requireDigit,
    Boolean requireSpecialCharacter
) {

}
