package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.controller.RoomTypeController;
import com.example.hotelmanagement.dto.response.RoomTypeResponse;
import com.example.hotelmanagement.model.RoomType;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class RoomTypeAssembler implements RepresentationModelAssembler<RoomType, EntityModel<RoomTypeResponse>> {
    @Override
    public EntityModel<RoomTypeResponse> toModel(RoomType entity) {
        RoomTypeResponse roomTypeResponse = new RoomTypeResponse(entity.getTypeName(), entity.getDescription(), entity.getBasePrice(), entity.getCapacity()) ;
        EntityModel<RoomTypeResponse> entityModel = EntityModel.of(roomTypeResponse,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).getRoomTypeById(entity.getId())).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).getRoomAllTypes()).withRel("Get all room types").withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).createRoomType(null)).withRel("Create new room type").withType("POST"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).updateRoomType(entity.getId(), null)).withRel("Update room type").withType("PUT"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).deleteRoomType(entity.getId())).withRel("Delete room type").withType("DELETE")
        );

        return entityModel;
    }
}
