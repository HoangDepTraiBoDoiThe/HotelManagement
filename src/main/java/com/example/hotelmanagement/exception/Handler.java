package com.example.hotelmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

record ExceptionResponse(String message, String causerMessage, String status, String webRequest) {}
record ValidationExceptionResponse(String message, String status, Map<String, String> validationErrors, String webRequest) {}


@RestControllerAdvice
class Handler {
    String messageTemplate = "Resource not found: %s";

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        String causerMessage = "";
        if (ex.getCause() != null) causerMessage = ex.getCause().getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(String.format(messageTemplate, errorMessage), causerMessage, HttpStatus.NOT_FOUND.name(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ClientBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> handleClientBadRequestException(ClientBadRequestException ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        String causerMessage = "";
        if (ex.getCause() != null) causerMessage = ex.getCause().getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(String.format(messageTemplate, errorMessage), causerMessage, HttpStatus.BAD_REQUEST.name(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ValidationExceptionResponse validationExceptionResponse = new ValidationExceptionResponse("Validation failed", HttpStatus.BAD_REQUEST.name(), errors, webRequest.getDescription(false));
        return ResponseEntity.badRequest().body(validationExceptionResponse);
    }
    
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthExceptions(AuthException ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        String causerMessage = "";
        if (ex.getCause() != null) causerMessage = ex.getCause().getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(String.format("My message: %s", errorMessage), causerMessage, HttpStatus.NOT_ACCEPTABLE.name(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<?> handleOtherException(Exception ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        String causerMessage = "";
        if (ex.getCause() != null) causerMessage = ex.getCause().getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(String.format("My message - Internal exception: %s", errorMessage), causerMessage, HttpStatus.INTERNAL_SERVER_ERROR.name(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
