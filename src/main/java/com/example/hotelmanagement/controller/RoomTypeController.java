package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.controller.assembler.RoomTypeAssembler;
import com.example.hotelmanagement.dto.request.RoomTypeRequest;
import com.example.hotelmanagement.dto.response.RoleResponse;
import com.example.hotelmanagement.dto.response.RoomTypeResponse;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms/room-types")
public class RoomTypeController {
    private final RoomTypeService roomTypeService;
    private final RoomTypeAssembler roomTypeAssembler;

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomTypeById(@PathVariable long id) {
        RoomType roomType = roomTypeService.getRoomTypeById(id);
        return ResponseEntity.ok(roomTypeAssembler.toModel(roomType));
    }
    
    @GetMapping
    public ResponseEntity<?> getRoomAllTypes() {
        List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
        var collectionModel = roomTypeAssembler.toCollectionModel(roomTypes);
        collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomTypeController.class).getRoomAllTypes()).withSelfRel().withType("GET"));
        return ResponseEntity.ok(collectionModel);
    }
    
    @PostMapping
    public ResponseEntity<?> createRoomType(@RequestBody @Validated RoomTypeRequest roomTypeRequest) {
        RoomType roomType = roomTypeService.createRoomType(roomTypeRequest);
        return ResponseEntity.ok(roomTypeAssembler.toModel(roomType));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoomType(@PathVariable long id, @RequestBody @Validated RoomTypeRequest roomTypeRequest) {
        return ResponseEntity.ok(roomTypeAssembler.toModel(roomTypeService.updateRoomType(id, roomTypeRequest)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomType(@PathVariable long id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.ok("Room type deleted successfully");
    }
}
