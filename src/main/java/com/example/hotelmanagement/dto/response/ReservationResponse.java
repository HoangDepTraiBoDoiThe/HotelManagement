package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse extends ResponseBase{
    Date checkIn;
    Date checkOut;
    BigDecimal totalPrice;
    EntityModel<?> owner;
    List<?> rooms;
    List<?> additionalUtilities;
    EntityModel<?> bill;

    public ReservationResponse(Reservation reservation, EntityModel<?> owner, List<?> rooms, List<?> additionalUtilities, EntityModel<?> bill) {
        super(reservation.getId());
        this.checkIn = reservation.getCheckIn();
        this.checkOut = reservation.getCheckOut();
        this.totalPrice = reservation.getTotalPrice();
        this.owner = owner;
        this.rooms = rooms;
        this.additionalUtilities = additionalUtilities;
        this.bill = bill;
    }
}
