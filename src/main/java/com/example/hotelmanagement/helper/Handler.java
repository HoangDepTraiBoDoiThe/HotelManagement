package com.example.hotelmanagement.helper;

import com.example.hotelmanagement.exception.ReservationException;
import com.example.hotelmanagement.exception.RoleException;
import com.example.hotelmanagement.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
class Handler {
    String notfoundMessage= "Resource not found: %s";
    @ExceptionHandler(RoleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> handleRoleNotFoundException(RoleException ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        ExceptionData exceptionData = new ExceptionData(String.format(notfoundMessage, errorMessage), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> handleUserNotFoundException(Exception ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        ExceptionData exceptionData = new ExceptionData(String.format(notfoundMessage, errorMessage), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> handleReservationNotFoundException(Exception ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        ExceptionData exceptionData = new ExceptionData(String.format(notfoundMessage, errorMessage), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    ResponseEntity<?> handleOtherException(Exception ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        ExceptionData exceptionData = new ExceptionData(String.format("My message - Internal exception: %s", errorMessage), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }
}
