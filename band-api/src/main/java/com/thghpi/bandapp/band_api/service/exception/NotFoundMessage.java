package com.thghpi.bandapp.band_api.service.exception;

/**
 * A record to build a NotFoundException in a service method
 * @param Long id the not found id in the database for the entity type
 * @param Class entity the entity class type concerned
 */
public record NotFoundMessage(Long id, Class<?> entity) { }