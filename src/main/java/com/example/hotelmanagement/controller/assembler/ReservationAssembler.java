package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.controller.ReservationController;
import com.example.hotelmanagement.model.Reservation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationAssembler implements RepresentationModelAssembler<Reservation, EntityModel<ReservationResponse>> {
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
