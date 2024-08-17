package com.example.hotelmanagement.dto.response.room;

import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.dto.response.RoomImageResponse;
import com.example.hotelmanagement.dto.response.room.roomType.RoomTypeResponse_Full;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Minimal;
import com.example.hotelmanagement.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse_Full extends RoomResponse_Basic {
    Number roomNumber;
    String roomStatus;
    BigDecimal roomBasePrice;
    String roomDescription;
    List<?> roomTypes;
    List<?> roomUtilities;
    CollectionModel<?> roomImages;
    EntityModel<ReservationResponse> currentReservation;

    public RoomResponse_Full(Room room, List<?> roomTypeResponses, List<?> roomUtilities, CollectionModel<EntityModel<RoomImageResponse>> roomImages, EntityModel<ReservationResponse> currentReservation) {
        super(room, roomImages, currentReservation);
        this.roomNumber = room.getRoomNumber();
        this.roomTypes = roomTypeResponses;
        this.roomUtilities = roomUtilities;
        this.roomStatus = room.getRoomStatus();
        this.roomBasePrice = room.getRoomBasePrice();
        this.roomDescription = room.getRoomDescription();
        this.roomImages = roomImages;
        this.currentReservation = currentReservation;
    }
}
