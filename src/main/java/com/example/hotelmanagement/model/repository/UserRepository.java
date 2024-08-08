package com.example.hotelmanagement.model.repository;

import com.example.hotelmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String userName);
    @Query("SELECT u FROM Users u JOIN u.roles r WHERE r.RoleName = :roleName")
    Collection<User> findUsersByRoleName(@Param("roleName") String roleName);
}
