package com.thghpi.bandapp.band_api.config;
import com.thghpi.bandapp.band_api.config.properties.JwtProperties;
import lombok.RequiredArgsConstructor;

import com.thghpi.bandapp.band_api.config.properties.CorsProperties;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({
    JwtProperties.class,
    CorsProperties.class
})
public class AppConfiguration {


}
