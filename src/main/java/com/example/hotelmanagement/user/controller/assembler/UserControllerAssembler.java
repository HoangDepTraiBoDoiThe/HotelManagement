package com.example.hotelmanagement.user.controller.assembler;

import com.example.hotelmanagement.user.controller.UserController;
import com.example.hotelmanagement.user.dto.response.UserResponse;
import com.example.hotelmanagement.user.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAssembler implements RepresentationModelAssembler<User, EntityModel<UserResponse>> {
    @Override
    public EntityModel<UserResponse> toModel(User entity) {
        EntityModel<UserResponse> entityModel = EntityModel.of(new UserResponse(entity), 
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)).withSelfRel().withType("GET"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)).withRel("Get all users").withType("GET")
        );
        
        return entityModel;
    }
}
