package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.ReservationAssembler;
import com.example.hotelmanagement.dto.response.BillResponse;
import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.dto.response.RoomResponse;
import com.example.hotelmanagement.dto.response.UserResponse;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Basic;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Minimal;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.Reservation;
import com.example.hotelmanagement.dto.request.ReservationRequest;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.ReservationRepository;
import com.example.hotelmanagement.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationAssembler reservationAssembler;
    private final UserService userService;
    private final BillService billService;
    private final RoomService roomService;
    private final UtilityService utilityService;
    
    public CollectionModel<EntityModel<ReservationResponse>> getAllReservations(Authentication authentication) {
        return reservationRepository.findAll().stream().map(reservation -> makeReservationResponse(reservation, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }

    public EntityModel<ReservationResponse> getReservation(long id, Authentication authentication) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        return makeReservationResponse(reservation, authentication);
    }    
    public Reservation getReservation_entity(long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }    

    public EntityModel<ReservationResponse> createReservation(ReservationRequest reservationRequest, Authentication authentication) {
        Set<Room> room = reservationRequest.getRoomIds().stream().map(roomId -> roomService.getRoomById_entity(roomId, authentication, true)).collect(Collectors.toSet());
        Set<Utility> additionalUtilities = reservationRequest.getAdditionalUtilityIds().stream().map(utilityId -> utilityService.getUtilityById_entity(utilityId, authentication, true)).collect(Collectors.toSet());
        User user = userService.getUserById_entity(reservationRequest.getOwnerId());
        
        Reservation newReservation = new Reservation(reservationRequest.getCheckIn(), reservationRequest.getCheckOut(), reservationRequest.getTotalPrice(), user, room, additionalUtilities);
        Reservation newCreatedReservation = reservationRepository.save(newReservation);
        
        return makeReservationResponse(newCreatedReservation, authentication);
    }
    
    public EntityModel<ReservationResponse> updateReservation(long id, ReservationRequest reservationRequest, Authentication authentication) {
        Reservation reservationToUpdate = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservationToUpdate.setCheckIn(reservationRequest.getCheckIn());
        reservationToUpdate.setCheckOut(reservationRequest.getCheckOut());
        reservationToUpdate.setTotalPrice(reservationRequest.getTotalPrice());
        Set<Room> rooms = reservationRequest.getRoomIds().stream().map(roomId -> roomService.getRoomById_entity(roomId, authentication, true)).collect(Collectors.toSet());
        reservationToUpdate.setRooms(rooms);

        Reservation updatedReservation = reservationRepository.save(reservationToUpdate);
        return makeReservationResponse(updatedReservation, authentication);
    }
    
    public void deleteReservation(long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }

    public EntityModel<ReservationResponse> makeReservationResponse(Reservation reservation, Authentication authentication) {
        // Todo: user
        EntityModel<UserResponse> userResponseEntityModel = userService.getUserById(reservation.getOwner().getId(), authentication);
        CollectionModel<EntityModel<UtilityResponse_Minimal>> additionalUtilityResponseEntityModels = reservation.getAdditionRoomUtility().stream().map(utility -> utilityService.makeUtilityResponse_Basic(utility, authentication, UtilityResponse_Minimal.class)).collect(Collectors.collectingAndThen(Collectors.toSet(), CollectionModel::of));
        CollectionModel<EntityModel<RoomResponse>> roomResponseEntityModels = reservation.getRooms().stream().map(room -> roomService.makeRoomResponse(room, authentication)).collect(Collectors.collectingAndThen(Collectors.toSet(), CollectionModel::of));
        EntityModel<BillResponse> billResponseEntityModel = billService.makeBillResponse(reservation.getReservationBill(), authentication);
        
        ReservationResponse reservationResponse = new ReservationResponse(reservation, userResponseEntityModel, roomResponseEntityModels, additionalUtilityResponseEntityModels, billResponseEntityModel);
        return reservationAssembler.toModel(reservationResponse, authentication);
    }

}
