package com.thghpi.bandapp.band_api.configuration;
import com.thghpi.bandapp.band_api.configuration.properties.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Central configuration for the API
 * AppConfiguration a class to make the imported properties accessible
 * by the different components of the API
 */
@Configuration
@EnableConfigurationProperties({
    JwtProperties.class,
    CorsProperties.class,
    PasswordProperties.class,
    PasswordEncoderProperties.class
})
public class AppConfiguration {


}
