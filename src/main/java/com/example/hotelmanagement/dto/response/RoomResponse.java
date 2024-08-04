package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.model.AppPhotos;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.reservation.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private long id;
    private Number roomNumber;
    private RoomType roomType;
    private boolean isReserved;
    private Reservation currentReservation;
    private List<Reservation> reservationHistories;
    private BigDecimal roomBasePrice;
    private String roomDescription;
    private AppPhotos roomImages;
}
