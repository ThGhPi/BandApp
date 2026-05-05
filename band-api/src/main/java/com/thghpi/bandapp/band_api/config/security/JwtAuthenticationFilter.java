package com.thghpi.bandapp.band_api.config.security;

import com.thghpi.bandapp.band_api.service.connection.JwtService;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Custom filter that intercepts incoming HTTP requests to perform JWT authentication.
 *Checks for the presence of a JWT token in the Authorization header, validates it,
 * sets the authentication in the security context
 * and handles any exceptions that may occur during the process by delegating to the HandlerExceptionResolver.
 * This filter is executed once per request and is responsible for ensuring that only authenticated users can access
 * protected resources by validating the JWT token included in the request headers.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Override of the doFilterInternal method to intercept incoming HTTP requests and perform JWT authentication.
     * Checks for the presence of a JWT token in the Authorization header, validates it,
     * sets the authentication in the security context
     * and handles any exceptions that may occur during the process by delegating to the HandlerExceptionResolver.
     * @param request the incoming HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to continue processing the request
     * @throws ServletException if an error occurs during filtering
     * @throws IOException if an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // If Authorization header is missing or doesn't start with "Bearer",
        // skip JWT processing and continue the filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Process the JWT token and handle any exceptions that may occur during validation, do filter chain once checked
        try {
            // Get rid of "Bearer " prefix to extract the JWT token
            final String jwt = authHeader.substring(7);
            // Get username for building user details and validating the token
            final String username = jwtService.extractUsername(jwt);

            // Get spring authentication status
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // If username is present and user is not authenticated yet, validate the token
            if (username != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // If token is valid, set authentication in the security context
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Set details about the request for the authentication token and set it in the security context
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
