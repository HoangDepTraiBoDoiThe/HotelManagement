package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


public record ReservationResponse(long id, Date checkIn, Date checkOut, BigDecimal totalPrice, long OwnerId) {

}
