package com.thghpi.bandapp.band_api.configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FixedClockedConfiguration {
    @Bean
    public Clock clock() {
        return Clock.fixed(
            Instant.parse("2026-07-03T12:00:00Z"),
            ZoneOffset.UTC
        );
    }
}
