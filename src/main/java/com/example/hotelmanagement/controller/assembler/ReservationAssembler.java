package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.controller.ReservationController;
import com.example.hotelmanagement.dto.response.BaseResponse;
import com.example.hotelmanagement.helper.StaticHelper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestControllerAdvice
public class ReservationAssembler {
    public <T extends BaseResponse> EntityModel<T> toModel(T entity, Authentication authentication) {
        Set<String> roles = StaticHelper.extractAllRoles(authentication);
        
        EntityModel<T> entityModel = EntityModel.of(entity, 
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).getReservation(entity.getId(), authentication)).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).getAllReservation(authentication)).withRel("Get all reservations").withType("GET")
        );
        if (roles.contains(ApplicationRole.ADMIN.name()) || roles.contains(ApplicationRole.MANAGER.name())) {
            entityModel.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).updateReservation(entity.getId(), null, authentication)).withRel("Update reservation").withType("PUT"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).deleteReservation(entity.getId(), authentication)).withRel("Delete reservation").withType("DELETE"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class).createReservation(null, authentication)).withRel("Create reservation").withType("POST")
            );
        } 
        
        return entityModel;
    }

    public <T extends BaseResponse> CollectionModel<EntityModel<T>> toCollectionModel(Iterable<T> entities, Authentication authentication) {
        return StreamSupport.stream(entities.spliterator(), false) //
                .map(t -> toModel(t, authentication)) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReservationController.class)).withSelfRel().withType("GET"));
    }
}
