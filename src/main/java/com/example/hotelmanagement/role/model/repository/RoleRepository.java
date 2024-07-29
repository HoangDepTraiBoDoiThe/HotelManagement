package com.example.hotelmanagement.role.model.repository;

import com.example.hotelmanagement.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
