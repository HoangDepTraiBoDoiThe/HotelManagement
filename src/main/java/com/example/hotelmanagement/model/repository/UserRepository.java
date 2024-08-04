package com.example.hotelmanagement.model.repository;

import com.example.hotelmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
