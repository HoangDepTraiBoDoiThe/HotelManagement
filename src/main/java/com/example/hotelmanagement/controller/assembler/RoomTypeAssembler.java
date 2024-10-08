package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.controller.RoomTypeController;
import com.example.hotelmanagement.dto.response.BaseResponse;
import com.example.hotelmanagement.helper.StaticHelper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class RoomTypeAssembler {
    public <T extends BaseResponse> EntityModel<T> toModel(T entity, Authentication authentication) {
        Set<String> roles = StaticHelper.extractAllRoles(authentication);
        EntityModel<T> entityModel = EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).getRoomTypeById(entity.getId(), authentication)).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).getRoomAllTypes(authentication)).withRel("Get all room types").withType("GET")
        );
        if (roles.contains(ApplicationRole.ADMIN.name()) || roles.contains(ApplicationRole.MANAGER.name())) {
            entityModel.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).createRoomType(null, authentication)).withRel("Create new room type").withType("POST"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).updateRoomType(entity.getId(), null, authentication)).withRel("Update room type").withType("PUT"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).deleteRoomType(entity.getId())).withRel("Delete room type").withType("DELETE")
            );
        }

        return entityModel;
    }

    public <T extends BaseResponse> CollectionModel<EntityModel<T>> toCollectionModel(Iterable<T> entities, Authentication authentication) {
        return StreamSupport.stream(entities.spliterator(), false) //
                .map(t -> toModel(t, authentication)) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }
}
