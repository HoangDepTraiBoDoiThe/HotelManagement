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
public class RoomResponseGuess {
    long id;
    Number roomNumber;
    CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponses;
    String roomStatus;
    BigDecimal roomBasePrice;
    String roomDescription;
    CollectionModel<EntityModel<RoomImageResponse>> roomImages;

    public RoomResponseGuess(Room room, CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponses, CollectionModel<EntityModel<RoomImageResponse>> roomImages) {
        this.id = room.getId();
        this.roomNumber = room.getRoomNumber();
        this.roomTypeResponses = roomTypeResponses;
        this.roomStatus = room.getRoomStatus();
        this.roomBasePrice = room.getRoomBasePrice();
        this.roomDescription = room.getRoomDescription();
        this.roomImages = roomImages;
    }
}
