package com.example.hotelmanagement.dto.response.room.roomType;

import com.example.hotelmanagement.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomType_BasicResponse extends RoomType_MinimalResponse {
    String description;
    BigDecimal basePrice;
    Number roomCapability;

    public RoomType_BasicResponse(RoomType roomType) {
        super(roomType);
        this.description = roomType.getDescription();
        this.basePrice = roomType.getBasePrice();
        this.roomCapability = roomType.getCapacity();
    }
}
