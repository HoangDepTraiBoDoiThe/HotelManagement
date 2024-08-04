package com.example.hotelmanagement.exception;

public class RoleException extends RuntimeException {
    public RoleException(String Message) {
        super("Role Exception: " + Message);
    }
}
