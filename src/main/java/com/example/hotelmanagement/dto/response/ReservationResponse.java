package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse extends BaseResponse {
    EntityModel<?> owner;
    EntityModel<?> bill;
    List<RoomReservationResponse> rookedRooms;

    public ReservationResponse(Reservation reservation, EntityModel<?> owner, List<RoomReservationResponse> rookedRooms, EntityModel<?> bill) {
        super(reservation.getId());
        this.owner = owner;
        this.bill = bill;
        this.rookedRooms = rookedRooms;
    }
}
