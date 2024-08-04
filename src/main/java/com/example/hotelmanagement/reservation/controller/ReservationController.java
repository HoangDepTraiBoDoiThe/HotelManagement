package com.example.hotelmanagement.reservation.controller;

import com.example.hotelmanagement.reservation.controller.assembler.ReservationControllerAssembler;
import com.example.hotelmanagement.reservation.dto.request.ReservationRequest;
import com.example.hotelmanagement.reservation.dto.response.ReservationResponse;
import com.example.hotelmanagement.reservation.model.Reservation;
import com.example.hotelmanagement.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationControllerAssembler reservationControllerAssembler;
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReservationResponse>>> getAllReservation() {
        List<Reservation> reservations = reservationService.getAllReservations();
        CollectionModel<EntityModel<ReservationResponse>> responseCollectionModel = reservationControllerAssembler.toCollectionModel(reservations);
        return ResponseEntity.ok(responseCollectionModel);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<EntityModel<ReservationResponse>> getReservation(@PathVariable long id) {
        Reservation reservation = reservationService.getReservation(id);
        EntityModel<ReservationResponse> reservationResponse = reservationControllerAssembler.toModel(reservation);
        return ResponseEntity.ok(reservationResponse);
    } 
    
    @PostMapping
    public ResponseEntity<EntityModel<ReservationResponse>> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = reservationService.createReservation(reservationRequest);
        EntityModel<ReservationResponse> reservationResponse = reservationControllerAssembler.toModel(newReservation);
        return ResponseEntity.ok(reservationResponse);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<ReservationResponse>> updateReservation(@PathVariable long id, @RequestBody ReservationRequest reservationRequest) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservationRequest);
        EntityModel<ReservationResponse> reservationResponse = reservationControllerAssembler.toModel(updatedReservation);
        return ResponseEntity.ok(reservationResponse);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
