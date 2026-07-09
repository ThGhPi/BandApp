package com.thghpi.bandapp.band_api.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for JSON Web Token (JWT) settings.
 * JwtProperties is used to bind the JWT configuration properties defined in the application properties file.
 * @param String secretKey The secret key used for signing and verifying JWT tokens.
 * @param Long expirationTime The expiration time (in milliseconds) for JWT tokens.
 */
@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(
    String secretKey,
    Long expirationTime
) { }