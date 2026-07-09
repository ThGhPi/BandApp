package com.thghpi.bandapp.band_api.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for password encoder settings.
 * PasswordEncoderProperties is used to bind the password encoder configuration properties defined in the application properties file.
 * @param Long strength The strength parameter for the password encoder, which determines the computational complexity of the hashing algorithm.
 */
@ConfigurationProperties(prefix = "app.password-encoder")
public record PasswordEncoderProperties (Long strength) { }