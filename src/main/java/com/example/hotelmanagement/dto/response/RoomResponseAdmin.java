package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseAdmin extends RoomResponseGuess{
    EntityModel<ReservationResponse> currentReservation;
    CollectionModel<EntityModel<ReservationResponse>> reservationHistories;

    public RoomResponseAdmin(Room room, CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponses, CollectionModel<EntityModel<RoomImageResponse>> roomImages, EntityModel<ReservationResponse> currentReservation, CollectionModel<EntityModel<ReservationResponse>> reservationHistories) {
        super(room.getId(), room.getRoomNumber(), roomTypeResponses, room.getRoomStatus(), room.getRoomBasePrice(), room.getRoomDescription(), roomImages);
        this.currentReservation = currentReservation;
        this.reservationHistories = reservationHistories;
    }
}
