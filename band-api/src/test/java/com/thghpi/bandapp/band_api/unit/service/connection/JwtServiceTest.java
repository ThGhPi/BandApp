package com.thghpi.bandapp.band_api.unit.service.connection;
import com.thghpi.bandapp.band_api.entity.Person;
import com.thghpi.bandapp.band_api.entity.enumeration.Role;
import com.thghpi.bandapp.band_api.repository.PersonRepository;
import com.thghpi.bandapp.band_api.configuration.properties.JwtProperties;
import com.thghpi.bandapp.band_api.configuration.security.AppUserDetailsService;
import com.thghpi.bandapp.band_api.service.connection.JwtService;
import com.thghpi.bandapp.band_api.service.connection.JwtServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;



/**
 * Unit tests for {@link JwtService}.
 * JwtServiceTest A class that contains unit tests for the JwtService class,
 * testing its functionality for generating and validating JWT tokens, rejecting expired tokens,
 * as well as extracting usernames from tokens.
 */
public class JwtServiceTest {
    
    /** The JwtServiceImpl instance to be tested */
    private JwtServiceImpl jwtService;
    /** The UserDetails instance to be used in tests */
    private UserDetails userDetails;
    /** The generated JWT token to be used in tests */
    private String token;

    /**
     * Sets up the test environment before each test method is executed.
     * This method initializes the JwtServiceImpl instance, creates a mock PersonRepository,
     * and generates a JWT token and set of UserDetails for a test user.
     */
    @BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties(
            "jy19b6M7BTKnaL38W92vMuldkwW7gREFc+W+hgNq7fOdd0EzphdgnAIxXI49TthIVeOlGhg+DvUC2ZHn7abzGg",
            3_600_000L
        );
        Clock fixedClock = Clock.fixed(
            Instant.parse("2026-07-03T12:00:00Z"),
            ZoneOffset.UTC
        );
        jwtService = new JwtServiceImpl(jwtProperties, fixedClock);

        Person person1 = new Person(
            1L, "Taylor", "Alice",
            "aliceT", "alice@example.com",
            "blank", Role.MEMBER,
            null, null, null,
            null, null, null
        );
        PersonRepository repository = mock(PersonRepository.class);
        when(repository.findByUsername("aliceT"))
            .thenReturn(Optional.of(person1));
        AppUserDetailsService userDetailsService = new AppUserDetailsService(repository);
        userDetails = userDetailsService.loadUserByUsername("aliceT");
        Instant now = Instant.now(fixedClock);
        Instant end = now.plusMillis(3_600_000);
        token = Jwts
                .builder()
                .subject(userDetails.getUsername())
                .audience().add(userDetails.getAuthorities().toString()).and()
                .expiration(Date.from(end))
                .issuedAt(Date.from(now))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey())), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Tests that the JwtService can generate a valid JWT token.
     */
    @Test
    void shouldGenerateValidToken() {
        String testToken = jwtService.generateToken(userDetails);
        assertNotNull(testToken);
        assertFalse(testToken.isBlank());
        assertTrue(jwtService.isTokenValid(testToken, userDetails));
        assertEquals(testToken, token);
    }

    /**
     * Tests that the JwtService can validate a valid JWT token.
     */
    @Test
    void shouldExtractUsername() {
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(userDetails.getUsername(), extractedUsername);
    }

    /**
     * Tests that the JwtService rejects an expired JWT token.
     */
    @Test
    void shouldRejectExpiredToken() {
        JwtProperties jwtProperties = new JwtProperties(
            "jy19b6M7BTKnaL38W92vMuldkwW7gREFc+W+hgNq7fOdd0EzphdgnAIxXI49TthIVeOlGhg+DvUC2ZHn7abzGg",
            3_600_000L
        );
        Clock clock = Clock.fixed(
            Instant.parse("2026-07-03T12:00:00Z"),
            ZoneOffset.UTC
        );
        Date now = Date.from(Instant.now(clock).minusMillis(5000));
        Date expiredDate = Date.from(Instant.now(clock).minusMillis(3000));

        String testToken = Jwts.builder()
        .subject(userDetails.getUsername())
                .audience().add(userDetails.getAuthorities().toString()).and()
                .expiration(expiredDate)
                .issuedAt(now)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey())), Jwts.SIG.HS512)
                .compact();
        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenValid(testToken, userDetails));
    }
}
