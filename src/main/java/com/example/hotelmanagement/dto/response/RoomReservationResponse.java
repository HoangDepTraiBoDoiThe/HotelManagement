package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.dto.response.room.RoomResponse_Basic;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Minimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomReservationResponse extends ResponseBase{
    private LocalDate checkin;
    private LocalDate checkout;
    private BigDecimal totalPrice;

    EntityModel<RoomResponse_Basic> room;
    List<EntityModel<UtilityResponse_Minimal>> additionalUtilities;
}
