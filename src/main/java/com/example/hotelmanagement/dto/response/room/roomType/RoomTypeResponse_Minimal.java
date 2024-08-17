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
public class RoomTypeResponse_Minimal extends ResponseBase {
    String roomTypeName;

    public RoomTypeResponse_Minimal(RoomType roomType) {
        super(roomType.getId());
        this.roomTypeName = roomType.getTypeName();
    }
}
