package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.model.Reservation;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationRequest {
    private Date checkIn;
    private Date checkOut;
    
    @NotBlank
    @PositiveOrZero
    private BigDecimal totalPrice;
    
    @NotBlank
    private long ownerId;
    
    @Size(min = 1)
    private List<Long> roomIds;
    private List<Long> additionalUtilityIds;
}
