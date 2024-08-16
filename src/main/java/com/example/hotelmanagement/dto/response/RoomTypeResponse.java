package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeResponse extends ResponseBase{
    String roomTypeName;
    String description;
    BigDecimal basePrice;
    Number roomCapability;
    CollectionModel<?> utilities;
    CollectionModel<?> rooms;

    public RoomTypeResponse(RoomType roomType, CollectionModel<?> utilities, CollectionModel<?> rooms) {
        super(roomType.getId());
        this.roomTypeName = roomType.getTypeName();
        this.description = roomType.getDescription();
        this.basePrice = roomType.getBasePrice();
        this.roomCapability = roomType.getCapacity();
        this.utilities = utilities;
        this.rooms = rooms;
    }
}
