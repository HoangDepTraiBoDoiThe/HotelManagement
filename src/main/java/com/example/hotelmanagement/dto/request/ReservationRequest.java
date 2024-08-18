package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.model.Reservation;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.model.Utility;
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
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationRequest {
    private Date checkIn;
    private Date checkOut;
    
    @PositiveOrZero
    private BigDecimal totalPrice;
    
    private long ownerId;
    
    @Size(min = 1)
    private List<Long> roomIds;
    private List<Long> additionalUtilityIds;

    public Reservation toModel(User user, Set<Room> rooms, Set<Utility> additionalUtilities) {
        Reservation newReservation = new Reservation(this.getCheckIn(), this.getCheckOut(), this.getTotalPrice(), user, rooms, additionalUtilities);
        return newReservation;
    }
}
