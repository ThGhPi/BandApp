package com.thghpi.bandapp.band_api.controller.exception;
import com.thghpi.bandapp.band_api.service.exception.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * An handler class for taking care of Unchecked Exeptions
 * and set appropriate http response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Catch and handle the NotFoundException exceptions to generate error 404 Not Found http response
     * @return a 404 NOT FOUND error with a message countaining the id and the entity type concerned
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleIdNotFound(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
            .body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
            )
        );
    }

}
