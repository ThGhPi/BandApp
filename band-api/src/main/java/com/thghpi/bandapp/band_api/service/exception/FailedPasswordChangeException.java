package com.thghpi.bandapp.band_api.service.exception;

/**
 * FailedPasswordChangeException extends RunTimeException, a class to manage error when changing password failed due to an erronous password.
 */
public class FailedPasswordChangeException extends RuntimeException {
    /**
     * Constructor for the class, use the super to generate the Exception
     * @param String message the message to be passed.
     */
    public FailedPasswordChangeException(String message) {
        super(message);
    }
}
