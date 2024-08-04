package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.controller.RoleController;
import com.example.hotelmanagement.dto.response.RoleResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class RoleAssembler implements RepresentationModelAssembler<RoleResponse, EntityModel<RoleResponse>> {

    @Override
    public EntityModel<RoleResponse> toModel(RoleResponse entity) {
        EntityModel<RoleResponse> roleEntityModel = EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).getRole(entity.getId())).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).getAllRoles()).withRel("Get all roles").withType("GET")
        );

        return roleEntityModel;
    }
}
