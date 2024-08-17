package com.example.hotelmanagement.dto.response.room;

import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.dto.response.ResponseBase;
import com.example.hotelmanagement.dto.response.RoomImageResponse;
import com.example.hotelmanagement.dto.response.room.roomType.RoomTypeResponse_Minimal;
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
public class RoomResponse_Basic extends ResponseBase {
    Number roomNumber;
    String roomStatus;
    BigDecimal roomBasePrice;
    String roomDescription;
    CollectionModel<?> roomImages;
    EntityModel<ReservationResponse> currentReservation;

    public RoomResponse_Basic(Room room, CollectionModel<EntityModel<RoomImageResponse>> roomImages, EntityModel<ReservationResponse> currentReservation) {
        super(room.getId());
        this.roomNumber = room.getRoomNumber();
        this.roomStatus = room.getRoomStatus();
        this.roomBasePrice = room.getRoomBasePrice();
        this.roomDescription = room.getRoomDescription();
        this.roomImages = roomImages;
        this.currentReservation = currentReservation;
    }
}
