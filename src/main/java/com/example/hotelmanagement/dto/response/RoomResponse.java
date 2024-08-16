package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse extends ResponseBase{
    Number roomNumber;
    CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponses;
    String roomStatus;
    BigDecimal roomBasePrice;
    String roomDescription;
    CollectionModel<EntityModel<RoomImageResponse>> roomImages;
    EntityModel<ReservationResponse> currentReservation;

    public RoomResponse(Room room, CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponses, CollectionModel<EntityModel<RoomImageResponse>> roomImages, EntityModel<ReservationResponse> currentReservation) {
        super(room.getId());
        this.roomNumber = room.getRoomNumber();
        this.roomTypeResponses = roomTypeResponses;
        this.roomStatus = room.getRoomStatus();
        this.roomBasePrice = room.getRoomBasePrice();
        this.roomDescription = room.getRoomDescription();
        this.roomImages = roomImages;
        this.currentReservation = currentReservation;
    }
}
