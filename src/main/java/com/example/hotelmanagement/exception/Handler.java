package com.example.hotelmanagement.exception;

import com.example.hotelmanagement.helper.ExceptionData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
class Handler {
    String messageTemplate = "Resource not found: %s";
    @ExceptionHandler(RoleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> handleRoleNotFoundException(RoleException ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        ExceptionData exceptionData = new ExceptionData(String.format(messageTemplate, errorMessage), HttpStatus.NOT_FOUND.name(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> handleUserException(UserException ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        ExceptionData exceptionData = new ExceptionData(String.format(messageTemplate, errorMessage), HttpStatus.BAD_REQUEST.name(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        ExceptionData exceptionData = new ExceptionData(String.format(messageTemplate, errorMessage), HttpStatus.NOT_FOUND.name(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> handleReservationNotFoundException(ReservationException ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        ExceptionData exceptionData = new ExceptionData(String.format(messageTemplate, errorMessage), HttpStatus.NOT_FOUND.name(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<?> handleOtherException(Exception ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        ExceptionData exceptionData = new ExceptionData(String.format("My message - Internal exception: %s", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR.name(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionData, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
