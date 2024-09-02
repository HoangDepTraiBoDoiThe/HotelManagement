package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.controller.RoomController;
import com.example.hotelmanagement.dto.response.BaseResponse;
import com.example.hotelmanagement.helper.StaticHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class RoomAssembler {
    public <T extends BaseResponse> EntityModel<T> toRoomModel(T responseDto, Authentication authentication) {
        Set<String> roles = StaticHelper.extractAllRoles(authentication);

        EntityModel<T> entityModel = EntityModel.of(responseDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).getRoomById(responseDto.getId(), null)).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).getRoomRooms(0, null)).withRel("Get all room").withType("GET")
                );

        if (roles.contains(ApplicationRole.ADMIN.name()) || roles.contains(ApplicationRole.MANAGER.name())) {
            entityModel.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).createRoom(null, authentication)).withRel("Create new room").withType("POST"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).updateRoom(responseDto.getId(), null, authentication)).withRel("Update room").withType("PUT"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).deleteRoom(responseDto.getId())).withRel("Delete room").withType("DELETE")
            );
        }

        return entityModel;
    }

    public <T extends BaseResponse> CollectionModel<EntityModel<T>> toCollectionModel(Iterable<T> responseDtos, Authentication authentication) {
        return StreamSupport.stream(responseDtos.spliterator(), false) //
                .map(t -> toRoomModel(t, authentication)) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).getRoomRooms(0, authentication)).withSelfRel().withType("GET"));
    }
}
