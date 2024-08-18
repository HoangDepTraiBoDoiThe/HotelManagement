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

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    
    @GetMapping("{id}")
    public ResponseEntity<EntityModel<UserResponse>> getUserById(@PathVariable long id, Authentication authentication) throws AuthenticationException {
        EntityModel<UserResponse> userResponseEntityModel = userService.getUserById(id, authentication);
        return ResponseEntity.ok(userResponseEntityModel);
    }
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserResponse>>> getAllUsers(Authentication authentication) {
        return ResponseEntity.ok(userService.getUsers(authentication));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<UserResponse>> updateUser(@PathVariable long id, User newUserData, Authentication authentication) throws AuthenticationException {
        return ResponseEntity.ok(userService.updateUser(id, newUserData, authentication));

    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }
}
