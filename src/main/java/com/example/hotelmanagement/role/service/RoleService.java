package com.example.hotelmanagement.role.service;

import com.example.hotelmanagement.role.exception.RoleException;
import com.example.hotelmanagement.role.model.Role;
import com.example.hotelmanagement.role.model.repository.RoleRepository;
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
        return roleRepository.findById(roleId).orElseThrow(() -> new RoleException("Role not found"));
    }

    public Role createRole(String roleName) {
        Role newRole = new Role(roleName);
        return roleRepository.save(newRole);
    }

    public Role updateRole(long roleId, String roleName) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleException("Role not found"));
        role.setRoleName(roleName);
        return roleRepository.save(role);
    }

    public void deleteRole(long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleException("Role not found"));
        roleRepository.delete(role);
    }
}
