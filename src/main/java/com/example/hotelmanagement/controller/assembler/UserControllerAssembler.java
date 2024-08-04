package com.example.hotelmanagement.controller.assembler;

import com.example.hotelmanagement.controller.UserController;
import com.example.hotelmanagement.dto.response.UserResponse;
import com.example.hotelmanagement.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAssembler implements RepresentationModelAssembler<User, EntityModel<UserResponse>> {
    @Override
    public EntityModel<UserResponse> toModel(User entity) {
        UserResponse userResponse = new UserResponse(entity);
        EntityModel<UserResponse> entityModel = EntityModel.of(userResponse, 
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(entity.getId())).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("Get all users").withType("GET")
        );
        
        return entityModel;
    }
}
