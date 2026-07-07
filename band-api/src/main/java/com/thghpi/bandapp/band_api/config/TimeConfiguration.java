package com.thghpi.bandapp.band_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Central time configuration for the app
 * TimeConfiguration a class that define a Clock Bean to be used throughout the app
 */
@Configuration
public class TimeConfiguration {

    /**
     * A Bean constructor to have a clock usable throughout the app
     * @return a clock sybchronized with the System time
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
