package com.thghpi.bandapp.band_api.service.exception;

/**
 * A class to be used for exception generation
 * when the password does not meet the required criteria
 * InvalidPasswordException
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
