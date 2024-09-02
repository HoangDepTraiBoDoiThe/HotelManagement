package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.room.RoomResponse_Basic;
import com.example.hotelmanagement.dto.response.room.RoomResponse_Full;
import com.example.hotelmanagement.helper.StaticHelper;
import com.example.hotelmanagement.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;
    private final StaticHelper staticHelper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable long id, Authentication authentication) {
        EntityModel<RoomResponse_Full> roomResponseAdminEntityModel = roomService.getRoomById(id, authentication);
        return ResponseEntity.ok(roomResponseAdminEntityModel);
    }

    @GetMapping
    public ResponseEntity<?> getRoomRooms(@RequestParam int page, Authentication authentication) {
        CollectionModel<EntityModel<RoomResponse_Basic>> responseEntityModels = roomService.getRooms(page, authentication);
        return ResponseEntity.ok(responseEntityModels);
    }

    @Secured("ADMIN")
    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody @Validated RoomRequest roomRequest, Authentication authentication) {
        EntityModel<RoomResponse_Full> roomResponseAdminEntityModel = roomService.createRoom(roomRequest, authentication);
        return ResponseEntity.ok(roomResponseAdminEntityModel);
    }

    @Secured("ADMIN")
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateRoom(@PathVariable long id, @RequestBody @Validated RoomRequest roomRequest, Authentication authentication) {
        return ResponseEntity.ok(roomService.updateRoomType(id, roomRequest, authentication));
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRoom(@PathVariable long id) {
        roomService.deleteRoomType(id);
        return ResponseEntity.ok("Room type deleted successfully");
    }
}
