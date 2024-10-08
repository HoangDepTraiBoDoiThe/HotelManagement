package com.example.hotelmanagement.helper;

import com.example.hotelmanagement.controller.assembler.*;
import com.example.hotelmanagement.dto.response.*;
import com.example.hotelmanagement.dto.response.room.RoomResponse_Basic;
import com.example.hotelmanagement.dto.response.room.RoomResponse_Full;
import com.example.hotelmanagement.dto.response.room.roomType.RoomType_FullResponse;
import com.example.hotelmanagement.dto.response.room.roomType.RoomType_MinimalResponse;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Basic;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Full;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Minimal;
import com.example.hotelmanagement.dto.response.user.UserResponse_Full;
import com.example.hotelmanagement.dto.response.user.UserResponse_Minimal;
import com.example.hotelmanagement.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class ServiceHelper {
    private final BillAssembler billAssembler;
    private final ReservationAssembler reservationAssembler;
    private final UserAssembler userAssembler;
    private final UtilityAssembler utilityAssembler;
    private final RoomAssembler roomAssembler;
    private final RoomTypeAssembler roomTypeAssembler;
    private final RoleAssembler roleAssembler;

    public <T extends BaseResponse> CollectionModel<EntityModel<T>> addLinksToPaginationResponse(CollectionModel<EntityModel<T>> entityModels, int page, IMyHelper helperInterface) {

        entityModels.add(
                linkTo(helperInterface.getPaginationMethodToLink(page)).withSelfRel().withType(HttpMethod.GET.name()),
                linkTo(helperInterface.getPaginationMethodToLink(page + 1)).withRel("Next").withType(HttpMethod.GET.name())
        );
        if (page > 0)
            entityModels.add(linkTo((helperInterface.getPaginationMethodToLink(page - 1))).withRel("Prev").withType(HttpMethod.GET.name()));

        return entityModels;
    }
    
    public <T extends UserResponse_Minimal> EntityModel<T> makeUserResponse(Class<T> responseType, User user, Authentication authentication) throws AuthenticationException {
        T response;
        try {
            if (UserResponse_Full.class.equals(responseType)) {
                response = (T) new UserResponse_Full(user);
            } else {
                response = responseType.getDeclaredConstructor(User.class).newInstance(user);
            }

            return userAssembler.toModel(response, authentication);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public EntityModel<RoleResponse> makeRoleResponse(Role role, Authentication authentication) {
        RoleResponse roleResponse = new RoleResponse(role);
        return roleAssembler.toModel(roleResponse, authentication);
    }

    public EntityModel<ReservationResponse> makeReservationResponse(Reservation reservation, Authentication authentication) {
        EntityModel<UserResponse_Minimal> userResponseEntityModel = null;
        try {
            userResponseEntityModel = makeUserResponse(UserResponse_Minimal.class, reservation.getOwner(), authentication);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        List<RoomReservationResponse> bookedRoomResponses = reservation.getRoomReservation().stream().map(roomReservation -> makeRoomReservationResponse(authentication, roomReservation)).toList();

        EntityModel<BillResponse> billResponseEntityModel = makeBillResponse(reservation.getReservationBill(), authentication);

        ReservationResponse reservationResponse = new ReservationResponse(reservation, userResponseEntityModel, bookedRoomResponses, billResponseEntityModel);
        return reservationAssembler.toModel(reservationResponse, authentication);
    }

    private RoomReservationResponse makeRoomReservationResponse(Authentication authentication, RoomReservation roomReservation) {
        EntityModel<RoomResponse_Basic> roomResponseEntityModel = makeRoomResponse(RoomResponse_Basic.class, roomReservation.getRoom(), authentication);
        List<EntityModel<UtilityResponse_Minimal>> utilityResponseEntityModel = roomReservation.getAdditionRoomUtility().stream().map(utility -> makeUtilityResponse(UtilityResponse_Minimal.class, utility, authentication)).toList();
        RoomReservationResponse reservationResponse = new RoomReservationResponse(roomReservation.getCheckin(), roomReservation.getCheckout(), roomReservation.getTotalPrice(), roomResponseEntityModel, utilityResponseEntityModel);
        return reservationResponse;
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
                CollectionModel<EntityModel<RoomResponse_Basic>> roomResponseBases = utility.getRooms().stream().map(room -> makeRoomResponse(RoomResponse_Basic.class, room, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));;
                CollectionModel<EntityModel<RoomType_MinimalResponse>> roomTypeCollectionModel = utility.getRoomTypes().stream().map(roomType -> makeRoomTypeResponse(RoomType_MinimalResponse.class, roomType, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));

                responseInstance = (T) new UtilityResponse_Full(utility, roomResponseBases, roomTypeCollectionModel, null);
            } else {
                // Create a basic or minimal response
                responseInstance = responseType.getDeclaredConstructor(Utility.class).newInstance(utility);
            }

            return utilityAssembler.toModel(responseInstance, authentication);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create an instance of " + responseType.getName(), e);
        }
    }

    public <T extends RoomResponse_Basic> EntityModel<T> makeRoomResponse(Class<T> responseType, Room room, Authentication authentication) {
        if (room == null) return null;

        try {
            T roomResponse;
            EntityModel<ReservationResponse> currentReservation = getCurrentReservationModel(room.getRoomReservations(), authentication).orElse(null);
            if (RoomResponse_Full.class.equals(responseType)) {
                List<EntityModel<RoomType_MinimalResponse>> roomTypeResponseModels = room.getRoomTypes().stream().map(roomType -> makeRoomTypeResponse(RoomType_MinimalResponse.class, roomType, authentication)).toList();
                List<EntityModel<UtilityResponse_Minimal>> utilityResponseModels = room.getRoomUtilities().stream().map(utility -> makeUtilityResponse(UtilityResponse_Minimal.class, utility, authentication)).toList();
                roomResponse = (T) new RoomResponse_Full(room, roomTypeResponseModels, utilityResponseModels, null, currentReservation);
            } else {
                roomResponse = responseType.getDeclaredConstructor(Room.class, CollectionModel.class, EntityModel.class).newInstance(room, null, currentReservation);
            }

            return roomAssembler.toRoomModel(roomResponse, authentication);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to create an instance of " + responseType.getName(), exception);
        }
    }
    private Optional<EntityModel<ReservationResponse>> getCurrentReservationModel(Set<RoomReservation> roomReservations, Authentication authentication) {
        if (roomReservations == null) return Optional.empty();
        return roomReservations.stream()
                .filter(roomReservation -> (roomReservation.getCheckout() != null && roomReservation.getCheckout().isAfter(StaticHelper.getCurrentLocalDate())))
                .findFirst()
                .map(roomReservation -> makeReservationResponse(roomReservation.getReservation(), authentication));
    }

    public <T extends RoomType_MinimalResponse> EntityModel<T> makeRoomTypeResponse(Class<T> responseType, RoomType roomType, Authentication authentication) {
        if (roomType == null) return null;

        T roomTypeResponse;
        try {
            if (RoomType_FullResponse.class.equals(responseType)) {
                CollectionModel<EntityModel<UtilityResponse_Basic>> utilityResponseModels = roomType.getRoomTypeUtilities().stream().map(utility -> makeUtilityResponse(UtilityResponse_Basic.class, utility, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
                CollectionModel<EntityModel<RoomResponse_Basic>> roomResponseModels = roomType.getRooms().stream().map(room -> makeRoomResponse(RoomResponse_Basic.class, room, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));

                roomTypeResponse = (T) new RoomType_FullResponse(roomType, utilityResponseModels, roomResponseModels);
            } else {
                roomTypeResponse = responseType.getDeclaredConstructor(RoomType.class).newInstance(roomType);
            }
        } catch (Exception exception) {
            throw new RuntimeException("Failed to create an instance of " + responseType.getName(), exception);
        }

        return roomTypeAssembler.toModel(roomTypeResponse, authentication);
    }
}
