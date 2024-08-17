package com.example.hotelmanagement.helper;

import com.example.hotelmanagement.controller.assembler.*;
import com.example.hotelmanagement.dto.response.*;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Basic;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Full;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Minimal;
import com.example.hotelmanagement.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServiceHelper {
    private final BillAssembler billAssembler;
    private final ReservationAssembler reservationAssembler;
    private final UserAssembler userAssembler;
    private final UtilityAssembler utilityAssembler;
    private final RoomAssembler roomAssembler;
    private final RoomTypeAssembler roomTypeAssembler;

    public EntityModel<UserResponse> makeUserResponse(User user, Authentication authentication) {
        UserResponse userResponse = new UserResponse(user);
        return userAssembler.toModel(userResponse, authentication);
    }
    
    public EntityModel<ReservationResponse> makeReservationResponse(Reservation reservation, Authentication authentication) {
        EntityModel<UserResponse> userResponseEntityModel = makeUserResponse(reservation.getOwner(), authentication);
        CollectionModel<EntityModel<UtilityResponse_Minimal>> additionalUtilityResponseEntityModels = reservation.getAdditionRoomUtility().stream().map(utility -> makeUtilityResponse(UtilityResponse_Minimal.class, utility, authentication)).collect(Collectors.collectingAndThen(Collectors.toSet(), CollectionModel::of));
        CollectionModel<EntityModel<RoomResponse>> roomResponseEntityModels = reservation.getRooms().stream().map(room -> makeRoomResponse(room, authentication)).collect(Collectors.collectingAndThen(Collectors.toSet(), CollectionModel::of));
        EntityModel<BillResponse> billResponseEntityModel = makeBillResponse(reservation.getReservationBill(), authentication);

        ReservationResponse reservationResponse = new ReservationResponse(reservation, userResponseEntityModel, roomResponseEntityModels, additionalUtilityResponseEntityModels, billResponseEntityModel);
        return reservationAssembler.toModel(reservationResponse, authentication);
    }

    public EntityModel<BillResponse> makeBillResponse(Bill bill, Authentication authentication) {
        if (bill == null) return null;
        EntityModel<ReservationResponse> reservationResponseEntityModel = makeReservationResponse(bill.getReservation(), authentication);
        BillResponse billResponse = new BillResponse(bill, reservationResponseEntityModel);
        return billAssembler.toModel(billResponse, authentication);
    }


    public <T extends UtilityResponse_Minimal> EntityModel<T> makeUtilityResponse(Class<T> responseType, Utility utility, Authentication authentication) {
        if (utility == null) return null;

        try {
            T responseInstance;

            if (UtilityResponse_Full.class.equals(responseType)) {
                // Build the full response with additional details
                List<ResponseBase> roomResponseBases = utility.getRooms().stream().map(room -> new ResponseBase(room.getId())).toList();
                CollectionModel<EntityModel<ResponseBase>> roomCollectionModel = roomAssembler.toCollectionModel(roomResponseBases, authentication);

                List<ResponseBase> roomTypeResponseBases = utility.getRoomTypes().stream().map(room -> new ResponseBase(room.getId())).toList();
                CollectionModel<EntityModel<RoomTypeResponse>> roomTypeCollectionModel = utility.getRoomTypes().stream().map(roomType -> makeRoomTypeResponse(roomType, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));

                responseInstance = (T) new UtilityResponse_Full(utility, roomCollectionModel, roomTypeCollectionModel, null);
            } else {
                // Create a basic or minimal response
                responseInstance = responseType.getDeclaredConstructor(Utility.class).newInstance(utility);
            }

            return utilityAssembler.toModel(responseInstance, authentication);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create an instance of " + responseType.getName(), e);
        }
    }

    public EntityModel<RoomResponse> makeRoomResponse(Room room, Authentication authentication) {
        if (room == null) return null;
        List<EntityModel<RoomTypeResponse>> roomTypeResponseModels = room.getRoomTypes().stream().map(roomType -> makeRoomTypeResponse(roomType, authentication)).toList();
        EntityModel<ReservationResponse> currentReservation = getCurrentReservationModel(room.getReservations(), authentication).orElse(null);
        RoomResponse roomResponse = new RoomResponse(room, roomTypeResponseModels, null, currentReservation);
        return roomAssembler.toRoomModel(roomResponse, authentication);
    }
    private Optional<EntityModel<ReservationResponse>> getCurrentReservationModel(Set<Reservation> reservations, Authentication authentication) {
        if (reservations == null) return Optional.empty();
        return reservations.stream()
                .filter(reservation -> reservation.getCheckOut().after(new Date()))
                .findFirst()
                .map(reservation -> makeReservationResponse(reservation, authentication));
    }

    public EntityModel<RoomTypeResponse> makeRoomTypeResponse(RoomType roomType, Authentication authentication) {
        CollectionModel<EntityModel<UtilityResponse_Basic>> utilityResponseModels = roomType.getRoomTypeUtilities().stream().map(utility -> makeUtilityResponse(UtilityResponse_Basic.class, utility, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
        CollectionModel<EntityModel<RoomResponse>> roomResponseModels = roomType.getRooms().stream().map(room -> makeRoomResponse(room, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));

        RoomTypeResponse roomTypeResponse = new RoomTypeResponse(roomType, utilityResponseModels, roomResponseModels);
        return roomTypeAssembler.toModel(roomTypeResponse, authentication);
    }
}
