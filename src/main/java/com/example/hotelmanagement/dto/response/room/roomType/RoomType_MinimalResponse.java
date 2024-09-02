package com.example.hotelmanagement.dto.response.room.roomType;

import com.example.hotelmanagement.dto.response.BaseResponse;
import com.example.hotelmanagement.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomType_MinimalResponse extends BaseResponse {
    String roomTypeName;

    public RoomType_MinimalResponse(RoomType roomType) {
        super(roomType.getId());
        this.roomTypeName = roomType.getTypeName();
    }
}
