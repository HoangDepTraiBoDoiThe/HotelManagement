package com.example.hotelmanagement.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record RoomTypeRequest(
        @NotBlank(message = "Room type name is required")
        @Size(max = 50, message = "Room type name must be less than 50 characters")
        String roomTypeName,
        List<Long> utilityIds,
        @NotBlank(message = "Description is required")
        @Size(max = 100, message = "Description must be less than 100 characters")
        String description,
        @PositiveOrZero(message = "Base price must be greater than or equal to 0")
        BigDecimal basePrice,
        @Positive(message = "Room capability must be greater than 0")
        @Min(value = 1, message = "Room capability must be greater than 0")
        Number roomCapability
) {
}
