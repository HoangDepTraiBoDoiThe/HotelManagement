package com.example.hotelmanagement.exception;

public class ReservationException extends RuntimeException {
    public ReservationException(String message) {
        super("Reservation exception - " + message);
    }
}
