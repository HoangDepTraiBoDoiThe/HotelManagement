package com.example.hotelmanagement.user.controller;

import com.example.hotelmanagement.user.controller.assembler.UserControllerAssembler;
import com.example.hotelmanagement.user.dto.response.UserResponse;
import com.example.hotelmanagement.user.model.User;
import com.example.hotelmanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserControllerAssembler userControllerAssembler;
    
    @GetMapping("{id}")
    public ResponseEntity<EntityModel<UserResponse>> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userControllerAssembler.toModel(user));
    }
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserResponse>>> getAllUsers() {
        CollectionModel<EntityModel<UserResponse>> userCollectionModel = userControllerAssembler.toCollectionModel(userService.getUsers());
        return ResponseEntity.ok(userCollectionModel);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<UserResponse>> updateUser(@PathVariable long id, User newUserData) {
        User updatedUser = userService.updateUser(id, newUserData);
        return ResponseEntity.ok(userControllerAssembler.toModel(updatedUser));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }
}
