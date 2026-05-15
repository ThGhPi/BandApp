package com.thghpi.bandapp.band_api.config.properties;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.password-encoder")
public class PasswordEncoderProperties {
    private final Long strength;
}
