package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.controller.ReservationController;
import com.example.hotelmanagement.dto.response.ResponseBase;
import com.example.hotelmanagement.model.Reservation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestControllerAdvice
public class ReservationAssembler {
    public <T extends ResponseBase> EntityModel<T> toModel(T entity, Authentication authentication) {
        return EntityModel.of(entity, 
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).getReservation(entity.getId(), authentication)).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).getAllReservation(authentication)).withRel("get-all-reservations").withType("GET")
        );
    }

    public <T extends ResponseBase> CollectionModel<EntityModel<T>> toCollectionModel(Iterable<T> entities, Authentication authentication) {
        return StreamSupport.stream(entities.spliterator(), false) //
                .map(t -> toModel(t, authentication)) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class)).withSelfRel().withType("GET"));
    }
}
