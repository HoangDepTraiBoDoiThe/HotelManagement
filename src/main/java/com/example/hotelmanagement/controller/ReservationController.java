package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.Auth.UserDetail.AuthUserDetail;
import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.dto.request.ReservationRequest;
import com.example.hotelmanagement.exception.AuthException;
import com.example.hotelmanagement.helper.SecurityHelper;
import com.example.hotelmanagement.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReservationResponse>>> getAllReservation(Authentication authentication) {
        CollectionModel<EntityModel<ReservationResponse>> reservations = reservationService.getAllReservations(authentication);
        return ResponseEntity.ok(reservations);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<EntityModel<ReservationResponse>> getReservation(@PathVariable long id, Authentication authentication) {
        EntityModel<ReservationResponse> responseEntityModel = reservationService.getReservation(id, authentication);
        return ResponseEntity.ok(responseEntityModel);
    } 
    
    @PostMapping("/create")
    public ResponseEntity<EntityModel<ReservationResponse>> createReservation(@RequestBody @Validated ReservationRequest reservationRequest, Authentication authentication) {
        EntityModel<ReservationResponse> responseEntityModel = reservationService.createReservation(reservationRequest, authentication);
        return ResponseEntity.ok(responseEntityModel);
    }
    
    @PutMapping("{id}/update")
    public ResponseEntity<EntityModel<ReservationResponse>> updateReservation(@PathVariable long id, @RequestBody ReservationRequest reservationRequest, Authentication authentication) {
        EntityModel<ReservationResponse> responseEntityModel = reservationService.updateReservation(id, reservationRequest, authentication);
        return ResponseEntity.ok(responseEntityModel);
    }
    
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id, Authentication authentication) {
        reservationService.deleteReservation(id, authentication);
        return ResponseEntity.noContent().build();
    }
}
