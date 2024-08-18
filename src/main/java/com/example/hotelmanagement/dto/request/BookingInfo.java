package com.example.hotelmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public record BookingInfo(long roomId, List<Long> additionalUtilityIds, LocalDate checkin, LocalDate checkout) {
}
