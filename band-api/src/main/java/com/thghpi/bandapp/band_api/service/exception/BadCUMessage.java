package com.thghpi.bandapp.band_api.service.exception;

import java.util.List;

/**
 * A record to build a BadCUException in a service method
 * @param Boolean creation to indicate creation with true and update with false
 * @param Class entity the entity class type concerned
 * @param String reason to indiccate what caused issues
 * @param List<Long> ids to pass ids of related entities when necessary (update)
 * @param String details to pass further details if needed
 */
public record BadCUMessage(
    Boolean creation, Class<?> entity, String reason, List<Long> ids, String details
) { }
