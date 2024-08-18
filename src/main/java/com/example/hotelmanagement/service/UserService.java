package com.example.hotelmanagement.service;

import com.example.hotelmanagement.Auth.dtos.RegisterRequest;
import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.dto.request.UserRequest;
import com.example.hotelmanagement.dto.response.user.UserResponse_Full;
import com.example.hotelmanagement.dto.response.user.UserResponse_Minimal;
import com.example.hotelmanagement.exception.AuthException;
import com.example.hotelmanagement.exception.ClientBadRequestException;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.helper.SecurityHelper;
import com.example.hotelmanagement.helper.ServiceHelper;
import com.example.hotelmanagement.helper.StaticHelper;
import com.example.hotelmanagement.model.Role;
import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.model.repository.RoleRepository;
import com.example.hotelmanagement.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServiceHelper serviceHelper;

    public CollectionModel<?> getUsers(Authentication authentication) {
        List<User> users = userRepository.findAll().stream().toList();
        return users.stream().map(user -> {
            try {
                if (SecurityHelper.checkAdminPermission(authentication) || SecurityHelper.checkOwningPermission(authentication, user.getId(), false))
                    return serviceHelper.makeUserResponse(UserResponse_Full.class, user, authentication);
                return serviceHelper.makeUserResponse(UserResponse_Minimal.class, user, authentication);
            } catch (AuthenticationException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }
    
    public EntityModel<?> getUserById(long id, Authentication authentication) throws AuthenticationException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User with id [%d] not found", id)));
        if (SecurityHelper.checkAdminPermission(authentication) || SecurityHelper.checkOwningPermission(authentication, user.getId(), false))
            return serviceHelper.makeUserResponse(UserResponse_Full.class, user, authentication);
        return serviceHelper.makeUserResponse(UserResponse_Minimal.class, user, authentication);
    }
    public User getUserById_entity(long id, boolean shouldThrowIfNull) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null && shouldThrowIfNull) throw new ResourceNotFoundException(String.format("User with id [%d] not found", id));
        return user;
    }
    
    public EntityModel<?> getUserByName(String userName, Authentication authentication) throws AuthenticationException {
        User user = userRepository.findUserByName(userName).orElseThrow(() -> new ResourceNotFoundException(String.format("User with name [%s] not found", userName)));
        if (SecurityHelper.checkAdminPermission(authentication) || SecurityHelper.checkOwningPermission(authentication, user.getId(), false))
            return serviceHelper.makeUserResponse(UserResponse_Full.class, user, authentication);
        return serviceHelper.makeUserResponse(UserResponse_Minimal.class, user, authentication);
    }

    public EntityModel<UserResponse_Full> createUser(RegisterRequest userRequest, Authentication authentication) throws AuthenticationException {
        User newUser = userRequest.toUser(passwordEncoder);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByRoleName(ApplicationRole.GUESS.name()).orElseThrow(() -> new ResourceNotFoundException(String.format("Role [%s] not found", ApplicationRole.GUESS.name()))));
        newUser.setRoles(roles);
        
        boolean isUserNameAvailable = userRepository.findUserByName(newUser.getName()).isEmpty();
        if (isUserNameAvailable) {
            return serviceHelper.makeUserResponse(UserResponse_Full.class, userRepository.save(newUser), authentication);
        }
        throw new ClientBadRequestException("Username is already taken.");
    }
    
    public EntityModel<UserResponse_Full> updateUser(long id, User newUserData, Authentication authentication) throws AuthenticationException {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!SecurityHelper.checkOwningPermission(authentication, existingUser.getId(), false) && !SecurityHelper.checkAdminPermission(authentication))
            throw new AuthException("You do not have permission for this request");

        existingUser.setName(newUserData.getName());
        existingUser.setEmail(newUserData.getEmail());
        existingUser.setPassword(newUserData.getPassword());
        existingUser.setPhoneNumber(newUserData.getPhoneNumber());
        existingUser.setRoles(newUserData.getRoles());

        return serviceHelper.makeUserResponse(UserResponse_Full.class, userRepository.save(existingUser), authentication);
    }
    
    public void deleteUser(long id, Authentication authentication) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!SecurityHelper.checkOwningPermission(authentication, existingUser.getId(), false) && !SecurityHelper.checkAdminPermission(authentication))
            throw new AuthException("You do not have permission for this request");
        userRepository.deleteById(id);
    }
    
}
