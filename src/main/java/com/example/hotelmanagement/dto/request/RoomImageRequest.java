package com.example.hotelmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RoomImageRequest(
        @NotBlank(message = "Image is required")
        Byte[] imageString,
        String description,
        @NotBlank(message = "Name is required")
        String name
        ) {

}
