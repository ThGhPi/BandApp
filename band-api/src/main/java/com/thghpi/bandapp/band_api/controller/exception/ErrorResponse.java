package com.thghpi.bandapp.band_api.controller.exception;

/**
 * A record used in the exception handler to build the body of a response entity from an exception
 * @param status the status expected for the http response entity
 * @param message the error message to be added to the body of the http response entity
 */
public record ErrorResponse(int status, String message) {

}
