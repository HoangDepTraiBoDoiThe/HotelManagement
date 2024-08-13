package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.controller.assembler.RoomAssembler;
import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.RoomResponseAdmin;
import com.example.hotelmanagement.dto.response.RoomResponseGuess;
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
    private final RoomAssembler roomAssembler;
    private final MyHelper myHelper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable long id, Authentication authentication) {
        List<String> roleStrings = myHelper.getUserApplicationRole(authentication);
        if (roleStrings.contains(ApplicationRole.ADMIN.name())) {
            RoomResponseAdmin roomResponseAdmin = roomService.getRoomById_Admin(id);
            return ResponseEntity.ok(roomAssembler.toRoomModel(roomResponseAdmin));
        }
        RoomResponseGuess roomResponseGuess = roomService.getRoomById_Guess(id);
        return ResponseEntity.ok(roomAssembler.toRoomModel(roomResponseGuess));
    }

    @GetMapping
    public ResponseEntity<?> getRoomAllRooms(Authentication authentication) {
        List<String> roles = myHelper.getUserApplicationRole(authentication);
        if (roles.contains(ApplicationRole.ADMIN.name())){
            List<RoomResponseAdmin> roomResponseAdmins = roomService.getAllRooms_Admin();
            CollectionModel<EntityModel<RoomResponseAdmin>> roomAssemblerCollectionModel_Admin = roomAssembler.toCollectionModel(roomResponseAdmins);
            roomAssemblerCollectionModel_Admin.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).getRoomAllRooms(authentication)).withSelfRel());
            return ResponseEntity.ok(roomAssemblerCollectionModel_Admin);
        }
        List<RoomResponseGuess> allRoomsGuess = roomService.getAllRooms_Guess();
        CollectionModel<EntityModel<RoomResponseGuess>> roomAssemblerCollectionModel_Guess = roomAssembler.toCollectionModel(allRoomsGuess);
        roomAssemblerCollectionModel_Guess.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class).getRoomAllRooms(authentication)).withSelfRel());
        return ResponseEntity.ok(roomAssemblerCollectionModel_Guess);
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody @Validated RoomRequest roomRequest) {
        RoomResponseAdmin newRoom = roomService.createRoom(roomRequest);
        return ResponseEntity.ok(roomAssembler.toRoomModel(newRoom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable long id, @RequestBody @Validated RoomRequest roomRequest) {
        return ResponseEntity.ok(roomAssembler.toRoomModel(roomService.updateRoomType(id, roomRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable long id) {
        roomService.deleteRoomType(id);
        return ResponseEntity.ok("Room type deleted successfully");
    }
}
