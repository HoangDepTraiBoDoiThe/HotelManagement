package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.controller.RoomController;
import com.example.hotelmanagement.controller.RoomTypeController;
import com.example.hotelmanagement.dto.response.RoomResponseAdmin;
import com.example.hotelmanagement.dto.response.RoomResponseGuess;
import com.example.hotelmanagement.dto.response.RoomTypeResponse;
import com.example.hotelmanagement.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class RoomAssembler {
    private final RoomTypeAssembler roomTypeAssembler;
    public <T extends RoomResponseGuess> EntityModel<T> toRoomModel(T responseDto) {
        return EntityModel.of(responseDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).getRoomById(responseDto.getId(), null)).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).getRoomAllRooms(null)).withRel("Get all room").withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).createRoom(null)).withRel("Create new room").withType("POST"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).updateRoom(responseDto.getId(), null)).withRel("Update room").withType("PUT"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).deleteRoom(responseDto.getId())).withRel("Delete room").withType("DELETE")
        );
    }


    public <T extends RoomResponseGuess> CollectionModel<EntityModel<T>> toCollectionModel(Iterable<T> responseDtos) {
        return StreamSupport.stream(responseDtos.spliterator(), false) //
                .map(this::toRoomModel) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }
}
