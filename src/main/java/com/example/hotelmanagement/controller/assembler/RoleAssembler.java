package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.controller.RoleController;
import com.example.hotelmanagement.dto.response.ResponseBase;
import com.example.hotelmanagement.dto.response.RoleResponse;
import com.example.hotelmanagement.helper.StaticHelper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class RoleAssembler{

    public <T extends ResponseBase> EntityModel<T> toModel(T entity, Authentication authentication) {
        Set<String> roles = StaticHelper.extractAllRoles(authentication);
        EntityModel<T> roleEntityModel = EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).getRole(entity.getId(), authentication)).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).getAllRoles(authentication)).withRel("Get all roles").withType("GET")
        );

        if (roles.contains(ApplicationRole.ADMIN.name()) || roles.contains(ApplicationRole.MANAGER.name())) {
            roleEntityModel.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).createRole(null, authentication)).withRel("Create new role").withType("POST"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).createRole(null, authentication)).withRel("Create new role").withType("POST"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).deleteRole(entity.getId())).withRel("Delete role").withType("DELETE")
            );
        } 

        return roleEntityModel;
    }

    public <T extends ResponseBase> CollectionModel<EntityModel<T>> toCollectionModel(Iterable<T> entities, Authentication authentication) {

        return StreamSupport.stream(entities.spliterator(), false) //
                .map(t -> toModel(t, authentication)) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).getAllRoles(authentication)).withSelfRel().withType("GET"));
    }
}
