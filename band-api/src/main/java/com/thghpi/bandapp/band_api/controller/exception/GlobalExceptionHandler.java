package com.thghpi.bandapp.band_api.controller.exception;
import com.thghpi.bandapp.band_api.service.exception.BadCUException;
import com.thghpi.bandapp.band_api.service.exception.ExistenceConflictException;
import com.thghpi.bandapp.band_api.service.exception.NotFoundException;

import io.jsonwebtoken.ExpiredJwtException;

import com.thghpi.bandapp.band_api.service.exception.InvalidPasswordException;

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
     * @param NotFoundException exception the exception thrown when an entity is not found in the repository
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

    /**
     * Catch and handle the BadCUException exceptions to generate error 400 Bad Request http response
     * @param BadCUException exception the exception thrown when a bad creation or update occurs
     * @return a 400 BAD REQUEST error with a message countaining the id given by the user, the entity type concerned, and the reason for failure
     */
    @ExceptionHandler(BadCUException.class)
    public ResponseEntity<ErrorResponse> handleBadCreationOrUpdate(BadCUException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
            )
        );
    }

    /**
     * Catch and handle the ExistenceConflictException exceptions to generate error 409 Conflict http response
     * @param ExistenceConflictException exception the exception thrown when a conflict occurs due to an existing entity
     * @return a 409 CONFLICT error with a message countaining the id given by the user, the entity type concerned, and the reason for failure
     */
    @ExceptionHandler(ExistenceConflictException.class)
    public ResponseEntity<ErrorResponse> handleExistenceConflict(ExistenceConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT.value())
            .body(new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
            )
        );
    }

    /**
     * Catch and handle the InvalidPasswordException exceptions to generate error 400 Bad Request http response
     * @param InvalidPasswordException exception the exception thrown when a password does not meet the required criteria
     * @return a 400 BAD REQUEST error with a message indicating the password does not meet the required criteria
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
            )
        );
    }

    /**
     * Catch and handle the ExpiredJwtException exceptions to generate error 401 Unauthorized http response
     * @param ExpiredJwtException exception the exception thrown when a JWT token has expired
     * @return a 401 UNAUTHORIZED error with a message indicating the token has expired
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwt(ExpiredJwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
            .body(new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage()
            ));
    }
}