package com.example.hotelmanagement.user.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super("User exception: " + message);
    }
}
