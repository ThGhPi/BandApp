package com.thghpi.bandapp.band_api.configuration.properties;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Cross-Origin Resource Sharing (CORS) settings.
 * CorsProperties is used to bind the CORS configuration properties defined in the application properties file.
 * @param List<String> allowedOrigins A list of allowed origins for CORS requests.
 */
@ConfigurationProperties(prefix = "app.cors")
public record CorsProperties (List<String> allowedOrigins) { }
