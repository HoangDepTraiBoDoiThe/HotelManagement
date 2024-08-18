package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.controller.assembler.RoleAssembler;
import com.example.hotelmanagement.dto.request.RoleRequest;
import com.example.hotelmanagement.dto.response.RoleResponse;
import com.example.hotelmanagement.model.Role;
import com.example.hotelmanagement.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/authentication/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RoleResponse>> getRole(@PathVariable long id, Authentication authentication) {
        return ResponseEntity.ok(roleService.getRoleById(id, authentication));
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles(Authentication authentication) {
        return ResponseEntity.ok(roleService.getAllRole(authentication));
    }

    @PostMapping
    public ResponseEntity<EntityModel<RoleResponse>> createRole(@RequestBody @Validated RoleRequest roleRequest, Authentication authentication) {
        return ResponseEntity.ok(roleService.createRole(roleRequest, authentication));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<RoleResponse>> updateRole(@PathVariable long id, @RequestBody @Validated RoleRequest roleRequest, Authentication authentication) {
        return ResponseEntity.ok(roleService.updateRole(id, roleRequest, authentication));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRole(@PathVariable long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
