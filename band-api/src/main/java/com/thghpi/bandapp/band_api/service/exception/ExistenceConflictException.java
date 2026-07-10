package com.thghpi.bandapp.band_api.service.exception;

/**
 * ExistenceConflictException for managing conflict error between existing and new entities
 */
public class ExistenceConflictException extends RuntimeException {

    /**
     * Constructor for the entity
     * Use the super from RuntimeException and the inner static method writeMessage
     * @param ExistenceConflictMessage message to be passed in the exception
     */
    public ExistenceConflictException(ExistenceConflictMessage message) {
        super(message.toString());
    }
}
