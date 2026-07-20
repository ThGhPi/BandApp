package com.thghpi.bandapp.band_api.service.exception;

/**
 * NotAuthenticatedException
 */
public class NotAuthenticatedException extends RuntimeException {
    public NotAuthenticatedException(String message) {
        super(message);
    }
}
