package com.example.hotelmanagement.role.controller;

import com.example.hotelmanagement.role.controller.assembler.RoleAssembler;
import com.example.hotelmanagement.role.dto.request.RoleRequest;
import com.example.hotelmanagement.role.dto.response.RoleResponse;
import com.example.hotelmanagement.role.model.Role;
import com.example.hotelmanagement.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/authentication/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleAssembler roleAssembler;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RoleResponse>> getRole(@PathVariable long id) {
        Role role = roleService.getRoleById(id);
        RoleResponse roleResponse = new RoleResponse(role);
        EntityModel<RoleResponse> roleResponseEntityModel = roleAssembler.toModel(roleResponse);
        return new ResponseEntity<>(roleResponseEntityModel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<RoleResponse>>> getAllRoles() {
        List<RoleResponse> roleResponse = RoleResponse.toRoleResponses(roleService.getAllRole());
        CollectionModel<EntityModel<RoleResponse>> collectionModel = roleAssembler.toCollectionModel(roleResponse);
        collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).getAllRoles()).withSelfRel().withType("GET"));
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<RoleResponse>> createRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = new RoleResponse(roleService.createRole(roleRequest.getRoleName()));
        EntityModel<RoleResponse> entityModel = roleAssembler.toModel(roleResponse);
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<RoleResponse>> updateRole(@PathVariable long id, @RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = new RoleResponse(roleService.updateRole(id, roleRequest.getRoleName()));
        EntityModel<RoleResponse> entityModel = roleAssembler.toModel(roleResponse);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("{id}")
    public void deleteRole(@PathVariable long id) {
        roleService.deleteRole(id);
    }
}
