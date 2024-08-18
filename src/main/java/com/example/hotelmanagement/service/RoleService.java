package com.example.hotelmanagement.service;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.controller.assembler.RoleAssembler;
import com.example.hotelmanagement.dto.request.RoleRequest;
import com.example.hotelmanagement.dto.response.RoleResponse;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.helper.ServiceHelper;
import com.example.hotelmanagement.model.Role;
import com.example.hotelmanagement.model.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final ServiceHelper serviceHelper;

    public CollectionModel<?> getAllRole(Authentication authentication) {
        CollectionModel<EntityModel<RoleResponse>> roleResponseCollectionModel = roleRepository.findAll().stream().map(role -> serviceHelper.makeRoleResponse(role, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
        return roleResponseCollectionModel;
    }

    public EntityModel<RoleResponse> getRoleById(long roleId, Authentication authentication) {
        return serviceHelper.makeRoleResponse(roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found")), authentication);
    }

    public EntityModel<RoleResponse> createRole(RoleRequest roleRequest, Authentication authentication) {
        Role newRole = new Role(roleRequest.getRoleName());
        Role newCreatedRole = roleRepository.save(newRole);
        return serviceHelper.makeRoleResponse(newCreatedRole, authentication);
    }

    public EntityModel<RoleResponse> updateRole(long roleId, RoleRequest roleRequest, Authentication authentication) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        role.setRoleName(roleRequest.getRoleName());
        Role updatedRole = roleRepository.save(role);
        return serviceHelper.makeRoleResponse(updatedRole, authentication);
    }

    public void deleteRole(long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        roleRepository.delete(role);
    }
}
