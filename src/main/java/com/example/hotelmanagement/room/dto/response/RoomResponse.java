package com.example.hotelmanagement.room.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private long id;
    private String roomNumber;
    private String roomType;
    private boolean isReserved;
    private long reservationId;
    private String reservationStartDate;
    private String reservationEndDate;
    private long customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private long roomPrice;
    private String roomDescription;
    private String roomImage;
}
