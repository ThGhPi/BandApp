package com.thghpi.bandapp.band_api.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for password settings.
 * PasswordProperties is used to bind the password configuration properties defined in the application properties file.
 * @param String regex The regular expression for validating password format.
 * @param Long minLength The minimum length for passwords.
 * @param Long maxLength The maximum length for passwords.
 * @param Boolean requireUppercase Whether passwords must include uppercase letters.
 * @param Boolean requireLowercase Whether passwords must include lowercase letters.
 * @param Boolean requireDigit Whether passwords must include digits.
 * @param Boolean requireSpecialCharacter Whether passwords must include special characters.
 */
@ConfigurationProperties(prefix = "app.password")
public record PasswordProperties(
    String regex,
    Long minLength,
    Long maxLength,
    Boolean requireUppercase,
    Boolean requireLowercase,
    Boolean requireDigit,
    Boolean requireSpecialCharacter
) { }