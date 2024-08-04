package com.example.hotelmanagement.service;

import com.example.hotelmanagement.exception.UserException;
import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll().stream().toList();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }
    
    public User updateUser(long id, User newUserData) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));

        existingUser.setName(newUserData.getName());
        existingUser.setEmail(newUserData.getEmail());
        existingUser.setPassword(newUserData.getPassword());
        existingUser.setPhoneNumber(newUserData.getPhoneNumber());
        existingUser.setRoles(newUserData.getRoles());

        return userRepository.save(existingUser);
    }
    
    public void deleteUser(long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
        userRepository.deleteById(id);
    }
}
