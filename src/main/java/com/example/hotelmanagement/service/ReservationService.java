package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.ReservationAssembler;
import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.helper.ServiceHelper;
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

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationAssembler reservationAssembler;
    private final UserService userService;
    private final ServiceHelper serviceHelper;
    private final RoomService roomService;
    private final UtilityService utilityService;
    
    public CollectionModel<EntityModel<ReservationResponse>> getAllReservations(Authentication authentication) {
        return reservationRepository.findAll().stream().map(reservation -> serviceHelper.makeReservationResponse(reservation, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }

    public EntityModel<ReservationResponse> getReservation(long id, Authentication authentication) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        return serviceHelper.makeReservationResponse(reservation, authentication);
    }    
    public Reservation getReservation_entity(long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }    

    public EntityModel<ReservationResponse> createReservation(ReservationRequest reservationRequest, Authentication authentication) {
        Set<Room> room = reservationRequest.getRoomIds().stream().map(roomId -> roomService.getRoomById_entity(roomId, authentication, true)).collect(Collectors.toSet());
        Set<Utility> additionalUtilities = reservationRequest.getAdditionalUtilityIds().stream().map(utilityId -> utilityService.getUtilityById_entity(utilityId, authentication, true)).collect(Collectors.toSet());
        User user = userService.getUserById_entity(reservationRequest.getOwnerId(), true);
        
        Reservation newReservation = new Reservation(reservationRequest.getCheckIn(), reservationRequest.getCheckOut(), reservationRequest.getTotalPrice(), user, room, additionalUtilities);
        Reservation newCreatedReservation = reservationRepository.save(newReservation);
        
        return serviceHelper.makeReservationResponse(newCreatedReservation, authentication);
    }
    
    public EntityModel<ReservationResponse> updateReservation(long id, ReservationRequest reservationRequest, Authentication authentication) {
        Reservation reservationToUpdate = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservationToUpdate.setCheckIn(reservationRequest.getCheckIn());
        reservationToUpdate.setCheckOut(reservationRequest.getCheckOut());
        reservationToUpdate.setTotalPrice(reservationRequest.getTotalPrice());
        Set<Room> rooms = reservationRequest.getRoomIds().stream().map(roomId -> roomService.getRoomById_entity(roomId, authentication, true)).collect(Collectors.toSet());
        reservationToUpdate.setRooms(rooms);

        Reservation updatedReservation = reservationRepository.save(reservationToUpdate);
        return serviceHelper.makeReservationResponse(updatedReservation, authentication);
    }
    
    public void deleteReservation(long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }



}
