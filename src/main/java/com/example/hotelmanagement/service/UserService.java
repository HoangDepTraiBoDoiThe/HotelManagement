package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.UserAssembler;
import com.example.hotelmanagement.dto.response.UserResponse;
import com.example.hotelmanagement.exception.ClientBadRequestException;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.helper.ServiceHelper;
import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;
    private final ServiceHelper serviceHelper;

    public CollectionModel<EntityModel<UserResponse>> getUsers(Authentication authentication) {
        List<User> users = userRepository.findAll().stream().toList();
        return users.stream().map(user -> serviceHelper.makeUserResponse(user, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }
    
    public EntityModel<UserResponse> getUserById(long id, Authentication authentication) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User with id [%d] not found", id)));
        return serviceHelper.makeUserResponse(user, authentication);
    }
    public User getUserById_entity(long id, boolean shouldThrowIfNull) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null && shouldThrowIfNull) throw new ResourceNotFoundException(String.format("User with id [%d] not found", id));
        return user;
    }
    
    public EntityModel<UserResponse> getUserByName(String userName, Authentication authentication) {
        User user = userRepository.findUserByName(userName).orElseThrow(() -> new ResourceNotFoundException(String.format("User with name [%s] not found", userName)));
        return serviceHelper.makeUserResponse(user, authentication);
    }

    public EntityModel<UserResponse> createUser(User newUser, Authentication authentication) {
        boolean isUserNameAvailable = userRepository.findUserByName(newUser.getName()).isEmpty();
        if (isUserNameAvailable) {
            userRepository.findUserByName(newUser.getName());
            return serviceHelper.makeUserResponse(userRepository.save(newUser), authentication);
        }
        throw new ClientBadRequestException("Username is already taken.");
    }
    
    public EntityModel<UserResponse> updateUser(long id, User newUserData, Authentication authentication) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        existingUser.setName(newUserData.getName());
        existingUser.setEmail(newUserData.getEmail());
        existingUser.setPassword(newUserData.getPassword());
        existingUser.setPhoneNumber(newUserData.getPhoneNumber());
        existingUser.setRoles(newUserData.getRoles());

        return serviceHelper.makeUserResponse(userRepository.save(existingUser), authentication);
    }
    
    public void deleteUser(long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
    
}
