package com.thghpi.bandapp.band_api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.password-encoder")
public record PasswordEncoderProperties (Long strength) {

}
