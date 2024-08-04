package com.example.hotelmanagement.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super("User exception: " + message);
    }
}
