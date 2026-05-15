package com.thghpi.bandapp.band_api.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {
    private final List<String> allowedOrigins;
}
