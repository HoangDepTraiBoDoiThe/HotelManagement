package com.example.hotelmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record RoomRequest(
        @NotBlank(message = "Room type name is required")
        @PositiveOrZero(message = "Room number must be greater than or equal to 0")
        Number roomNumber,

        @NotBlank(message = "Room floor name is required")
        @PositiveOrZero(message = "Room floor must be greater than or equal to 0")
        Number roomFloor,
        
        List<Long> roomTypeIds,
        List<Long> utilityIds,
        
        boolean isReserved,
        
        @NotBlank
        @PositiveOrZero(message = "Base price must be greater than or equal to 0")
        BigDecimal roomBasePrice,
        
        @NotBlank(message = "Room name is required")
        @Size(max = 50, message = "Room name must be less than 50 characters")
        String roomName,
        
        @NotBlank(message = "Description is required")
        @Size(max = 100, message = "Description must be less than 100 characters")
        String roomDescription,
        
        List<RoomImageRequest> roomImages
        ) {
}
