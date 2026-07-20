package com.thghpi.bandapp.band_api.controller.exception;

import com.thghpi.bandapp.band_api.service.exception.*;
import io.jsonwebtoken.ExpiredJwtException;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
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
     * Catch and handle the NoSuchElementException exceptions to generate error 404 Not Found http response
     * @param NoSuchElementException exception the exception thrown when an entity is not found in the repository
     * @return a 404 NOT FOUND error with a message countaining the id and the entity type concerned
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleIdNotFound(NoSuchElementException exception) {
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
     * Catch and handle the DataIntegrityViolationException exceptions to generate error 409 Conflict http response
     * @param DataIntegrityViolationException exception the exception thrown by the dao when a conflict occurs due to an existing entity in database
     * @return a 409 CONFLICT error with a message countaining the id given by the user, the entity type concerned, and the reason for failure
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleExistenceConflict(DataIntegrityViolationException exception) {
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
     * Catch and handle the FailedPasswordChangeException exceptions to generate error 403 Forbidden http response
     * @param FailedPasswordChangeException exception the exception thrown when the password change failed to a wrong password
     * @return a 403 FORBIDDEN error with a message indicating the password provided did not fit
     */
    @ExceptionHandler(FailedPasswordChangeException.class)
    public ResponseEntity<ErrorResponse> handleFailedPasswordChange(FailedPasswordChangeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
            .body(new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage()
            ));
    }

    /**
     * Catch and handle the NotAuthenticatedException exceptions to generate error 401 UNAUTHORIZED http response
     * @param NotAuthenticatedException exception the exception thrown when an operation outside current authority is attempted
     * @return a 401 UNAUTHORIZED error with a message indicating the authentication doesn't match the necessary authorities for executing the request
     */
    @ExceptionHandler(NotAuthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleNotAuthenticated(NotAuthenticatedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
            .body(new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage()
            ));
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

    /**
     * Catch and handle the IDontKnowException exceptions to generate error 500 INTERNAL_SERVER_ERROR http response
     * @param IDontKnowException exception the exception thrown when a strange thing happen
     * @return a 500 INTERNAL_SERVER_ERROR error with a message indicating something strange happened
     */
    @ExceptionHandler(IDontKnowException.class)
    public ResponseEntity<ErrorResponse> handleIDontKnowWhat(IDontKnowException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
            ));
    }

    /**
     * Catch and handle the RuntimeException exceptions to generate error 500 INTERNAL_SERVER_ERROR http response
     * @param RuntimeException exception the exception thrown when it's the death of me
     * @return a 500 INTERNAL_SERVER_ERROR error with a message indicating i don't know what
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleOtherRuntime(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
            ));
    }


}