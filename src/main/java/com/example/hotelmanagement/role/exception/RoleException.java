package com.example.hotelmanagement.role.exception;

public class RoleException extends RuntimeException {
    public RoleException(String Message) {
        super("Role Exception: " + Message);
    }
}
