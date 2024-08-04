package com.example.hotelmanagement.reservation.exception;

public class ReservationException extends RuntimeException {
    public ReservationException(String message) {
        super("Reservation exception - " + message);
    }
}
