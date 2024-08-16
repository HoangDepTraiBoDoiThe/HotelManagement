package com.example.hotelmanagement.dto.response.roomUtility;

import com.example.hotelmanagement.model.Reservation;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.model.Utility;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UtilityResponse_Full extends UtilityResponse_Basic {
    private CollectionModel<?> reservations;
    private CollectionModel<?> rooms;
    private CollectionModel<?> roomTypes;
    public UtilityResponse_Full(Utility utility, CollectionModel<?> rooms, CollectionModel<?> roomTypes, CollectionModel<?> reservations) {
        super(utility);
        this.reservations = reservations;
        this.rooms = rooms;
        this.roomTypes = roomTypes;
    }
}
