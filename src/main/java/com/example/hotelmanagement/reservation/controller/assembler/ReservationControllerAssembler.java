package com.example.hotelmanagement.reservation.controller.assembler;

import com.example.hotelmanagement.reservation.controller.ReservationController;
import com.example.hotelmanagement.reservation.dto.response.ReservationResponse;
import com.example.hotelmanagement.reservation.model.Reservation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationControllerAssembler implements RepresentationModelAssembler<Reservation, EntityModel<ReservationResponse>> {
    @Override
    public EntityModel<ReservationResponse> toModel(Reservation entity) {
        ReservationResponse reservationResponse = new ReservationResponse(entity);
        EntityModel<ReservationResponse> responseEntityModel = EntityModel.of(reservationResponse, 
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).getReservation(entity.getId())).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).getAllReservation()).withRel("get-all-reservations").withType("GET")
        );
        
        return responseEntityModel;
    }
}
