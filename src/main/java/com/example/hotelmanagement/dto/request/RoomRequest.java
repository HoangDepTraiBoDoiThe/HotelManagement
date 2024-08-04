package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.model.AppPhotos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    private Number roomNumber;
    private long roomTypeId;
    private boolean isReserved;
    private long reservationId;
    private BigDecimal roomPrice;
    private String roomDescription;
    private AppPhotos roomImages;
}
