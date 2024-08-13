package com.example.hotelmanagement.service;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.Role;
import com.example.hotelmanagement.model.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public Role getRoleById(long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

    public Role createRole(ApplicationRole roleName) {
        Role newRole = new Role(roleName.name());
        return roleRepository.save(newRole);
    }

    public Role updateRole(long roleId, ApplicationRole roleName) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        role.setRoleName(roleName.name());
        return roleRepository.save(role);
    }

    public void deleteRole(long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        roleRepository.delete(role);
    }
}
