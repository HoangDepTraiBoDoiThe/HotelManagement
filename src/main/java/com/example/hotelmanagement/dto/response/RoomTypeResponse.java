package com.example.hotelmanagement.dto.response;

import java.math.BigDecimal;

public record RoomTypeResponse (
        String roomTypeName,
        String description,
        BigDecimal basePrice,
        Number roomCapability
) {
}
