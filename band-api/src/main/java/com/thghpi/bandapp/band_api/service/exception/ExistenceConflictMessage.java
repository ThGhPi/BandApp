package com.thghpi.bandapp.band_api.service.exception;

import java.util.List;

/**
 * ExistenceConflictMessage
 */
public record ExistenceConflictMessage(
    Boolean creation, Class<?> entity, String reason, List<Long> ids, String details
) {
    /**
     * Override of the toString method to return a String with the following structure :
     * "Can't create/update EntityName with ErrorReason : EntityName withIDs theIDs with ErrorDetails"
     * @return String with the message to be passed in the exception
     */
    @Override
    public String toString() {
        String writtenMessage = "Can't " +
            (creation ? "create" : "update")
            + " " + entity.getSimpleName()
            + (reason == null ? "" : " " + reason);
        if (ids != null) {
            writtenMessage = writtenMessage.concat(
                " : " + entity.getSimpleName()
                + " with IDs " + ids.toString()
                + " " + details
            );
        } else if (details != null) {
            writtenMessage = writtenMessage.concat(". " + details);
        }
        writtenMessage = writtenMessage.concat(".");
        return writtenMessage;
    }
}
