package com.thghpi.bandapp.band_api.service.connection;
import com.thghpi.bandapp.band_api.config.properties.JwtProperties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.time.Clock;
import java.time.Instant;

import javax.crypto.SecretKey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service class responsible for handling JWT token generation, validation, and claim extraction.
 * It uses the JJWT library to create and parse JWT tokens.
 * It relies on properties defined in the application configuration for the secret key and token expiration time.
 */
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    /**
     * The object containing JWT-related properties such as the secret key and expiration time,
     * injected from the application configuration.
     */
    private final JwtProperties jwtProperties;
    /**
     * The injected central clock from {@link TimeConfiguration}
     */
    private final Clock clock;

    /**
     * Method to generate a JWT token for the given user details without any extra claims.
     * @param userDetails the user details for which to generate the token
     * @return the generated JWT token
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Method to generate a JWT token with the given extra claims and user details.
     * @param extraClaims a map of additional claims to be included in the token
     * @param userDetails the user details for which to generate the token
     * @return the generated JWT token
     */
    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails);
    }

    /**
     * Method to build and sign a JWT token with the given extra claims, user details, and expiration time.
     * @param extraClaims a map of additional claims to be included in the token
     * @param userDetails the user details for which to build the token
     * @return the generated JWT token
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        final Instant now = Instant.now(clock);
        final Instant expirationTime = now.plusMillis(getJwtExpiration());
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .audience().add(userDetails.getAuthorities().toString()).and()
                .claims(extraClaims)
                .expiration(Date.from(expirationTime))
                .issuedAt(Date.from(now))
                .signWith(getSignInKey(), getSigningAlgorithm())
                .compact();
    }

    /**
     * Method to validate the JWT token against the user details and expiration.
     * @param token the JWT token to be validated
     * @param userDetails the user details against which to validate the token
     * @return true if the token is valid and non expired, false otherwise
     */
    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    
    /**
     * Method to extract the username (subject) from the JWT token.
     * @param token the JWT token from which to extract the username
     * @return the username extracted from the token
     */
    @Override
    public String extractUsername(String token) { return extractClaim(token, Claims::getSubject); }
    
    /**
     * Method to check if the JWT token is expired.
     * @param token the JWT token to be checked
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(Date.from(Instant.now(clock)));
    }
    
    /**
     * Method to extract the expiration date from the JWT token.
     * @param token the JWT token from which to extract the expiration date
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * Generic method to extract any claim from the JWT token using a claims resolver function.
     * @param token the JWT token from which to extract the claim
     * @param claimsResolver the function to resolve the claim of type T from the Claims object
     * @return the extracted claim of type T
     */
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Method to verify the legitimacy of the token and extract all claims from it.
     * @param token the JWT token to be parsed
     * @return the Claims object containing all claims from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .clock(() -> Date.from(Instant.now(clock)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Centralized method to get the JWT expiration time from the properties.
     * @return the expiration time in milliseconds for JWT tokens
     */
    private Long getJwtExpiration() {
        return jwtProperties.expirationTime();
    }

    /**
     * Centralized method to get the signing key for JWTs.
     * @return the SecretKey used for signing JWTs
     */
    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey()));
    }

    /**
     * Centralized method to get the signing algorithm for JWTs.
     * @return the MacAlgorithm used for signing JWTs
     */
    private MacAlgorithm getSigningAlgorithm() {
        return Jwts.SIG.HS512;
    }
}
