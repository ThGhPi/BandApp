package com.thghpi.bandapp.band_api.service.connection;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling JWT token generation, validation, and claim extraction.
 * It uses the JJWT library to create and parse JWT tokens.
 * It relies on properties defined in the application configuration for the secret key and token expiration time.
 */
@Service
public class JwtService {
    /**
     * The secret key used for signing JWT tokens, injected from application properties.
     */
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    /**
     * The expiration time for JWT tokens in milliseconds, injected from application properties.
     */
    @Value("${security.jwt.expiration-time}")
    private Long jwtExpiration;

    /**
     * Method to generate a JWT token for the given user details without any extra claims.
     * @param userDetails the user details for which to generate the token
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Method to generate a JWT token with the given extra claims and user details.
     * @param extraClaims a map of additional claims to be included in the token
     * @param userDetails the user details for which to generate the token
     * @return the generated JWT token
     */
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
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .audience().add(userDetails.getAuthorities().toString()).and()
                .claims(extraClaims)
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignInKey(), getSigningAlgorithm())
                .compact();
    }

    /**
     * Method to validate the JWT token against the user details and expiration.
     * @param token the JWT token to be validated
     * @param userDetails the user details against which to validate the token
     * @return true if the token is valid and non expired, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    
    /**
     * Method to extract the username (subject) from the JWT token.
     * @param token the JWT token from which to extract the username
     * @return the username extracted from the token
     */
    public String extractUsername(String token) { return extractClaim(token, Claims::getSubject); }
    
    /**
     * Method to check if the JWT token is expired.
     * @param token the JWT token to be checked
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
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
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Centralized method to get the signing key for JWTs.
     * @return the SecretKey used for signing JWTs
     */
    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    /**
     * Centralized method to get the signing algorithm for JWTs.
     * @return the MacAlgorithm used for signing JWTs
     */
    private MacAlgorithm getSigningAlgorithm() {
        return Jwts.SIG.HS512;
    }
}
