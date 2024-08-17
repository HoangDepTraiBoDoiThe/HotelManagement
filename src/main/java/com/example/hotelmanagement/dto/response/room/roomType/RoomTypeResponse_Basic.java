package com.example.hotelmanagement.dto.response.room.roomType;

import com.example.hotelmanagement.dto.response.ResponseBase;
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
public class RoomTypeResponse_Basic extends RoomTypeResponse_Minimal {
    String description;
    BigDecimal basePrice;
    Number roomCapability;

    public RoomTypeResponse_Basic(RoomType roomType) {
        super(roomType);
        this.description = roomType.getDescription();
        this.basePrice = roomType.getBasePrice();
        this.roomCapability = roomType.getCapacity();
    }
}
