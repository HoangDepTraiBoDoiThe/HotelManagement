package com.example.hotelmanagement.model.repository;

import com.example.hotelmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String userName);
}
