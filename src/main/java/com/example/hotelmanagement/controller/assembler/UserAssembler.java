package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.controller.AuthController;
import com.example.hotelmanagement.controller.UserController;
import com.example.hotelmanagement.dto.response.ResponseBase;
import com.example.hotelmanagement.helper.StaticHelper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UserAssembler {
    public <T extends ResponseBase> EntityModel<T> toModel(T entity, Authentication authentication) throws AuthenticationException {
        Set<String> roles = StaticHelper.extractAllRoles(authentication);
        
        EntityModel<T> entityModel = EntityModel.of(entity, 
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(entity.getId(), authentication)).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthController.class).userRegister(null)).withSelfRel().withType("POST"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthController.class).userLogin(null)).withSelfRel().withType("POST"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers(authentication)).withRel("Get all users").withType("GET")
        );

        if (roles.contains(ApplicationRole.ADMIN.name()) || roles.contains(ApplicationRole.MANAGER.name())) {
            entityModel.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUser(entity.getId(), null, authentication)).withRel("Update user").withType("PUT"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).deleteUser(entity.getId(), authentication)).withRel("Delete user").withType("DELETE")
            );
        }

        return entityModel;
    }

    public <T extends ResponseBase> CollectionModel<EntityModel<T>> toCollectionModel(Iterable<T> entities, Authentication authentication) {
        return StreamSupport.stream(entities.spliterator(), false) //
                .map(t -> {
                    try {
                        return toModel(t, authentication);
                    } catch (AuthenticationException e) {
                        throw new RuntimeException(e);
                    }
                }) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }
}
