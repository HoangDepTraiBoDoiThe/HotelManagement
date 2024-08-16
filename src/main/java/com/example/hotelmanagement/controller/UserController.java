package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.controller.assembler.UserAssembler;
import com.example.hotelmanagement.dto.response.UserResponse;
import com.example.hotelmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserAssembler userAssembler;
    
    @GetMapping("{id}")
    public ResponseEntity<EntityModel<UserResponse>> getUserById(@PathVariable long id, Authentication authentication) {
        EntityModel<UserResponse> userResponseEntityModel = userService.getUserById(id, authentication);
        return ResponseEntity.ok(userResponseEntityModel);
    }
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserResponse>>> getAllUsers(Authentication authentication) {
        return ResponseEntity.ok(userService.getUsers(authentication));
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserResponse>> createUser(@RequestBody User newUser, Authentication authentication) {
        User createdUser = userService.createUser(newUser);
        return ResponseEntity.ok(userAssembler.toModel(new UserResponse(createdUser), authentication));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<UserResponse>> updateUser(@PathVariable long id, User newUserData, Authentication authentication) {
        User updatedUser = userService.updateUser(id, newUserData);
        return ResponseEntity.ok(userAssembler.toModel(new UserResponse(updatedUser), authentication));

    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }
}
