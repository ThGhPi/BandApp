package com.thghpi.bandapp.band_api.service.exception;

/**
 * A class to be used for exception generation
 * when there are invalid arguments in a entity send for creation or update
 */
public class BadCUException extends RuntimeException {

    /**
     * Constructor for the entity
     * Use the super from RuntimeException and the inner static method writeMessage
     * @param BadCUMessage message to be passed in the exception
     */
    public BadCUException(BadCUMessage message) {
        super(writeMessage(message));
    }

    /**
     * A static method to use in the constructor
     * generate a coherent String message to report the exception
     * @param BadCUMessage message to transform into a String
     * @return a String with a coherent message
     */
    public static String writeMessage(BadCUMessage message) {
        String writtenMessage = "Can't " +
            (message.creation() ? "create" : "update")
            + " " + message.entity().getSimpleName()
            + (message.reason() == null ? "" : " " + message.reason());
        if (message.ids() != null) {
            writtenMessage = writtenMessage.concat(
                " : " + message.entity().getSimpleName()
                + " with IDs " + message.ids().toString()
                + " " + message.details()
            );
        } else if (message.details() != null) {
            writtenMessage = writtenMessage.concat(". " + message.details());
        }
        writtenMessage = writtenMessage.concat(".");
        return writtenMessage;
    }
}
