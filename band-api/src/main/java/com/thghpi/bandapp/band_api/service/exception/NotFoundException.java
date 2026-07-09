package com.thghpi.bandapp.band_api.service.exception;

import java.lang.RuntimeException;

/**
 * A class to be used for exception generation
 * when an id is not found for a certain entity type
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructor using super from it's extending class,
     * passing it the correct String to be used exception message
     * @param NotFoundMessage message containing the id and the entity type class
     */
    public NotFoundException(NotFoundMessage message) {
        super(message.toString());
    }
}
