package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.request.RoomTypeRequest;
import com.example.hotelmanagement.dto.response.room.roomType.RoomTypeResponse_Basic;
import com.example.hotelmanagement.dto.response.room.roomType.RoomTypeResponse_Full;
import com.example.hotelmanagement.dto.response.room.roomType.RoomTypeResponse_Minimal;
import com.example.hotelmanagement.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        CollectionModel<EntityModel<RoomTypeResponse_Basic>> roomTypeResponses = roomTypeService.getAllRoomTypes(authentication);
        return ResponseEntity.ok(roomTypeResponses);
    }

    @Secured("ADMIN")

    @PostMapping("/create")
    public ResponseEntity<?> createRoomType(@RequestBody @Validated RoomTypeRequest roomTypeRequest, Authentication authentication) {
        EntityModel<RoomTypeResponse_Minimal> roomTypeServiceRoomType = roomTypeService.createRoomType(roomTypeRequest, authentication);
        return ResponseEntity.ok(roomTypeServiceRoomType);
    }
    
    @Secured("ADMIN")
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateRoomType(@PathVariable long id, @RequestBody @Validated RoomTypeRequest roomTypeRequest, Authentication authentication) {
        return ResponseEntity.ok(roomTypeService.updateRoomType(id, roomTypeRequest, authentication));
    }
    
    @Secured("ADMIN")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRoomType(@PathVariable long id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.ok("Room type deleted successfully");
    }
}
