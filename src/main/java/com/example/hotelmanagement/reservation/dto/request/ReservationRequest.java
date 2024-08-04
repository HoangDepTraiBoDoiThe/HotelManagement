package com.example.hotelmanagement.reservation.dto.request;

import com.example.hotelmanagement.reservation.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationRequest {
    private Date checkIn;
    private Date checkOut;
    private BigDecimal totalPrice;
    private long OwnerId;

    public ReservationRequest(Reservation reservation) {
        this.checkIn = reservation.getCheckIn();
        this.checkOut = reservation.getCheckOut();
        this.totalPrice = reservation.getTotalPrice();
    }
}
