package com.example.hotelmanagement.exception;

public class AuthException extends RuntimeException {
    public AuthException(String Message) {
        super("Authority Exception: " + Message);
    }
}
