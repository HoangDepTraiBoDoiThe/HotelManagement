package com.example.hotelmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UtilityRequest(
        @NotBlank(message = "Utility name is required")
        @Size(max = 100, message = "Utility name should be less than or equal to 100 characters")
        String utilityName,
        @NotBlank(message = "Description name is required")
        @Size(max = 500, message = "Description should be less than or equal to 500 characters")
        String utilityDescription,
        @NotBlank(message = "Price name is required")
        @PositiveOrZero(message = "Price must be positive or zero(Free)")
        BigDecimal baseUtilityPrice,
        boolean status) 
{}
