package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.UserAssembler;
import com.example.hotelmanagement.dto.response.UserResponse;
import com.example.hotelmanagement.exception.ClientBadRequestException;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    public CollectionModel<EntityModel<UserResponse>> getUsers(Authentication authentication) {
        List<User> users = userRepository.findAll().stream().toList();
        List<UserResponse> userResponses = users.stream().map(UserResponse::new).toList();
        return userAssembler.toCollectionModel(userResponses, authentication);
    }

    public EntityModel<UserResponse> getUserById(long id, Authentication authentication) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userAssembler.toModel(new UserResponse(user), authentication);
    }
    public User getUserById_entity(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    public User getUserByName(String userName) {
        return userRepository.findUserByName(userName).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User createUser(User newUser) {
        boolean isUserNameAvailable = userRepository.findUserByName(newUser.getName()).isEmpty();
        if (isUserNameAvailable) {
            userRepository.findUserByName(newUser.getName());
            return userRepository.save(newUser);
        }
        throw new ClientBadRequestException("Username is already taken.");
    }
    
    public User updateUser(long id, User newUserData) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        existingUser.setName(newUserData.getName());
        existingUser.setEmail(newUserData.getEmail());
        existingUser.setPassword(newUserData.getPassword());
        existingUser.setPhoneNumber(newUserData.getPhoneNumber());
        existingUser.setRoles(newUserData.getRoles());

        return userRepository.save(existingUser);
    }
    
    public void deleteUser(long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
