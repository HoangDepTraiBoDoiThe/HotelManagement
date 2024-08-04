package com.example.hotelmanagement.reservation.dto.response;

import com.example.hotelmanagement.reservation.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private long id;
    private Date checkIn;
    private Date checkOut;
    private BigDecimal totalPrice;
    private long OwnerId;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.checkIn = reservation.getCheckIn();
        this.checkOut = reservation.getCheckOut();
        this.totalPrice = reservation.getTotalPrice();
    }
}
