package com.thghpi.bandapp.band_api.dto.request;

/**
 * LoginRequest record used as a data transfer object
 * for incoming login request 
 * @param username the name used for connection
 * @param trialPassword the password send to be tested
 * @see Person
 */
public record LoginRequest(
    String username,
    String trialPassword
) { }
