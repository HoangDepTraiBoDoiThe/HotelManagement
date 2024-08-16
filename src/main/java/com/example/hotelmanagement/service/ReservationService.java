package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.ReservationAssembler;
import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.dto.response.UserResponse;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.Reservation;
import com.example.hotelmanagement.dto.request.ReservationRequest;
import com.example.hotelmanagement.model.repository.ReservationRepository;
import com.example.hotelmanagement.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationAssembler reservationAssembler;
    private final UserService userService;
    
    public CollectionModel<EntityModel<ReservationResponse>> getAllReservations(Authentication authentication) {
        List<ReservationResponse> reservationResponses = reservationRepository.findAll().stream().map(reservation -> makeReservationResponse(reservation, authentication)).toList();
        CollectionModel<EntityModel<ReservationResponse>> collectionModel = reservationAssembler.toCollectionModel(reservationResponses, authentication);
        return collectionModel;
    }

    public EntityModel<ReservationResponse> getReservation(long id, Authentication authentication) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        return reservationAssembler.toModel(makeReservationResponse(reservation, authentication), authentication);
    }    
    public Reservation getReservation_entity(long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }    

    public EntityModel<ReservationResponse> createReservation(ReservationRequest reservationRequest, Authentication authentication) {
//        TODO: a room shouldnt be able to be in 2 active reservations at the same time.  
        User user  = userService.getUserById_entity(reservationRequest.getOwnerId());
        Reservation newReservation = new Reservation(reservationRequest.getCheckIn(), reservationRequest.getCheckOut(), reservationRequest.getTotalPrice(), user);
        Reservation newCreatedReservation = reservationRepository.save(newReservation);
        return reservationAssembler.toModel(makeReservationResponse(newCreatedReservation, authentication), authentication);
    }
    
    public EntityModel<ReservationResponse> updateReservation(long id, ReservationRequest reservationRequest, Authentication authentication) {
        Reservation reservationToUpdate = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservationToUpdate.setCheckIn(reservationRequest.getCheckIn());
        reservationToUpdate.setCheckOut(reservationRequest.getCheckOut());
        reservationToUpdate.setTotalPrice(reservationRequest.getTotalPrice());

        Reservation updatedReservation = reservationRepository.save(reservationToUpdate);
        return reservationAssembler.toModel(makeReservationResponse(updatedReservation, authentication), authentication);
    }
    
    public void deleteReservation(long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }

    private ReservationResponse makeReservationResponse(Reservation reservation, Authentication authentication) {
        ReservationResponse reservationResponse = new ReservationResponse(reservation);
        EntityModel<UserResponse> userResponseEntityModel = userService.getUserById(reservation.getOwner().getId(), authentication);
        reservationResponse.setOwner(userResponseEntityModel);
        return reservationResponse;
    }

}
