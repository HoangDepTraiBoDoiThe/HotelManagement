package com.example.hotelmanagement.dto.response;

import jakarta.validation.constraints.NotBlank;

public record RoomImageResponse(Byte[] imageString, String description, String name) {
}
