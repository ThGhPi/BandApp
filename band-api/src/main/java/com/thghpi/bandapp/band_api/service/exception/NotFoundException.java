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
     * with the following structure :
     * "EntityName with ID theID not found"
     * @param NotFoundMessage message containing the id and the entity type class
     */
    public NotFoundException(NotFoundMessage message) {
        super(message.entity().getSimpleName() + " with ID " + message.id() + " not found");
    }
}
