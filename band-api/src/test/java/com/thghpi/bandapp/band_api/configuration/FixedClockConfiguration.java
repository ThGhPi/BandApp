package com.thghpi.bandapp.band_api.configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;

/**
 * Configuration class to provide a fixed Clock bean for testing purposes.
 * This allows for consistent and predictable time-related behavior in tests.
 * FixedClockConfiguration
 */
@TestConfiguration
public class FixedClockConfiguration {

    /**
     * Provides a fixed Clock bean set to a specific instant in time (2026-07-03T12:00:00Z).
     * This is useful for testing time-dependent functionality in a consistent manner.
     * @return a Clock instance fixed at the specified instant in UTC.
     */
    @Bean
    public Clock clock() {
        return Clock.fixed(
            Instant.parse("2026-07-03T12:00:00Z"),
            ZoneOffset.UTC
        );
    }
}
