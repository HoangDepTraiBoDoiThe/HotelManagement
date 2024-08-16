package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.RoomResponse;
import com.example.hotelmanagement.helper.MyHelper;
import com.example.hotelmanagement.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final MyHelper myHelper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable long id, Authentication authentication) {
        EntityModel<RoomResponse> roomResponseAdminEntityModel = roomService.getRoomById(id, authentication);
        return ResponseEntity.ok(roomResponseAdminEntityModel);
    }

    @GetMapping
    public ResponseEntity<?> getRoomAllRooms(Authentication authentication) {
        CollectionModel<EntityModel<RoomResponse>> responseEntityModels = roomService.getAllRooms(authentication);
        return ResponseEntity.ok(responseEntityModels);
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody @Validated RoomRequest roomRequest, Authentication authentication) {
        EntityModel<RoomResponse> roomResponseAdminEntityModel = roomService.createRoom(roomRequest, authentication);
        return ResponseEntity.ok(roomResponseAdminEntityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable long id, @RequestBody @Validated RoomRequest roomRequest, Authentication authentication) {
        return ResponseEntity.ok(roomService.updateRoomType(id, roomRequest, authentication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable long id) {
        roomService.deleteRoomType(id);
        return ResponseEntity.ok("Room type deleted successfully");
    }
}
