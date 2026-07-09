package com.thghpi.bandapp.band_api.service.exception;

/**
 * A class to be used for exception generation
 * when there are invalid arguments in a entity send for creation or update
 */
public class BadCUException extends RuntimeException {

    /**
     * Constructor for the entity
     * Use the super from RuntimeException
     * @param BadCUMessage message to be passed in the exception
     */
    public BadCUException(BadCUMessage message) {
        super(message.toString());
    }
}
