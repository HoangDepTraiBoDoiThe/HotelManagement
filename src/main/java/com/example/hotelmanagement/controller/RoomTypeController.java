package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.controller.assembler.RoomTypeAssembler;
import com.example.hotelmanagement.dto.request.RoomTypeRequest;
import com.example.hotelmanagement.dto.response.RoleResponse;
import com.example.hotelmanagement.dto.response.RoomTypeResponse;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms/room-types")
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<?>> getRoomTypeById(@PathVariable long id, Authentication authentication) {
        return ResponseEntity.ok(roomTypeService.getRoomTypeById(id, authentication));
    }
    
    @GetMapping
    public ResponseEntity<?> getRoomAllTypes(Authentication authentication) {
        CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponses = roomTypeService.getAllRoomTypes(authentication);
        return ResponseEntity.ok(roomTypeResponses);
    }
    
    @PostMapping
    public ResponseEntity<?> createRoomType(@RequestBody @Validated RoomTypeRequest roomTypeRequest, Authentication authentication) {
        EntityModel<RoomTypeResponse> roomTypeServiceRoomType = roomTypeService.createRoomType(roomTypeRequest, authentication);
        return ResponseEntity.ok(roomTypeServiceRoomType);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoomType(@PathVariable long id, @RequestBody @Validated RoomTypeRequest roomTypeRequest, Authentication authentication) {
        return ResponseEntity.ok(roomTypeService.updateRoomType(id, roomTypeRequest, authentication));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomType(@PathVariable long id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.ok("Room type deleted successfully");
    }
}
