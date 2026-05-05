package com.thghpi.bandapp.band_api.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {

    private final List<String> allowedOrigins;

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }
}
