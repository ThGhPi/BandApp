package com.thghpi.bandapp.band_api.service.exception;

/**
 * A record to build a NotFoundException in a service method
 * @param Long id the not found id in the database for the entity type
 * @param Class entity the entity class type concerned
 */
public record NotFoundMessage(Long id, Class<?> entity) {

    /**
     * Override of the toString method to return a String with the following structure :
     * "EntityName with ID theID not found"
     * @return String with the message to be passed in the exception
     */
    @Override
    public String toString() {
        return entity.getSimpleName() + " with ID " + id + " not found.";
    }
}