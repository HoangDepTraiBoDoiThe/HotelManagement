package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.model.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationRequest {
    @Size(min = 1)
    public Set<BookingInfo> bookingInfo;
    private long ownerId;
}
