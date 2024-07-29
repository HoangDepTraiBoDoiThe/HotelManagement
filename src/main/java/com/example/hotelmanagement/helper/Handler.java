package com.example.hotelmanagement.helper;

import com.example.hotelmanagement.role.exception.RoleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class Handler {
    String notfoundMessage= "Resource not found: %s";
    @ExceptionHandler(RoleException.class)
    public ResponseEntity<?> handleRoleNotFoundException(String message, WebRequest webRequest) {
        ExceptionData exceptionData = new ExceptionData(String.format(notfoundMessage, message), webRequest.getDescription(true));
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherException(String message, WebRequest webRequest) {
        ExceptionData exceptionData = new ExceptionData(String.format("My message - Internal exception: %s", message), webRequest.getDescription(true));
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }
}
