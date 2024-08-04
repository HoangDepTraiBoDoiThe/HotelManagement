package com.example.hotelmanagement.user.model.repository;

import com.example.hotelmanagement.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
