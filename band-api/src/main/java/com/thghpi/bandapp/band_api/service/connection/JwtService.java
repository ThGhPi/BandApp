package com.thghpi.bandapp.band_api.service.connection;

import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    Boolean isTokenValid(String token, UserDetails userDetails);
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
