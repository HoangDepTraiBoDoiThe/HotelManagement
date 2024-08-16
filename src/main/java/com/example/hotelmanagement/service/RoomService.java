package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.RoomAssembler;
import com.example.hotelmanagement.controller.assembler.RoomTypeAssembler;
import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.dto.response.RoomResponse;
import com.example.hotelmanagement.dto.response.RoomTypeResponse;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.helper.RoomStatus;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.RoomRepository;
import com.example.hotelmanagement.model.repository.RoomTypeRepository;
import com.example.hotelmanagement.model.repository.UtilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ReservationService reservationService;
    private final RoomTypeService roomTypeService;
    private final UtilityService utilityService;
    private final RoomTypeAssembler roomTypeAssembler;
    private final RoomAssembler roomAssembler;

    public CollectionModel<EntityModel<RoomResponse>> getAllRooms(Authentication authentication) {
        List<Room> rooms = roomRepository.findAllWithReservations();
        List<RoomResponse> roomResponse = rooms.stream().map(room -> new RoomResponse(
                room,
                roomTypeAssembler.toCollectionModel(room.getRoomTypes()),
                null,
                getCurrentReservationModel(room, authentication).orElse(null)
        )).collect(Collectors.toList());
        
        return roomAssembler.toCollectionModel(roomResponse, authentication);
    }

    public EntityModel<RoomResponse> getRoomById(Long id, Authentication authentication) {
        Room room = roomRepository.findWithReservationsById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponseModels = roomTypeAssembler.toCollectionModel(room.getRoomTypes());

        RoomResponse RoomResponse = new RoomResponse(room, roomTypeResponseModels, null, getCurrentReservationModel(room, authentication).orElse(null));
        return roomAssembler.toRoomModel(RoomResponse, authentication);
    }

    @Transactional
    public EntityModel<RoomResponse> createRoom(RoomRequest roomRequest, Authentication authentication) {
        Room newRoom = new Room(roomRequest.roomName(), roomRequest.roomDescription(), roomRequest.roomBasePrice(), roomRequest.roomFloor(), roomRequest.roomNumber(), RoomStatus.ROOM_AVAILABLE.name());
        Set<RoomType> roomTypes = roomRequest.roomTypeIds().stream().map(aLong -> roomTypeService.getRoomTypeById(aLong, authentication)).collect(Collectors.toSet());
        Set<Utility> utilities = roomRequest.utilityIds().stream().map(aLong -> utilityService.getUtilityById(aLong, authentication)).collect(Collectors.toSet());
        newRoom.setRoomTypes(roomTypes);
        newRoom.setRoomUtilities(utilities);
        Room newCreatedRoom = roomRepository.save(newRoom);

        CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponseModels = roomTypeAssembler.toCollectionModel(newCreatedRoom.getRoomTypes());
        RoomResponse RoomResponse = new RoomResponse(newCreatedRoom, roomTypeResponseModels, null, null);
        return roomAssembler.toRoomModel(RoomResponse, authentication);
    }

    @Transactional
    public EntityModel<RoomResponse> updateRoomType(long id, RoomRequest roomRequest, Authentication authentication) {
        Room room = roomRepository.findWithReservationsById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        room.setRoomName(roomRequest.roomName());
        room.setRoomDescription(roomRequest.roomDescription());
        room.setRoomBasePrice(roomRequest.roomBasePrice());
        Set<RoomType> roomTypes = roomRequest.roomTypeIds().stream().map(aLong -> roomTypeService.getRoomTypeById(aLong, authentication)).collect(Collectors.toSet());
        room.setRoomTypes(roomTypes);
        Room updatedRoom = roomRepository.save(room);
        
        CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponseModels = roomTypeAssembler.toCollectionModel(updatedRoom.getRoomTypes());
        RoomResponse RoomResponse = new RoomResponse(updatedRoom, roomTypeResponseModels, null, getCurrentReservationModel(updatedRoom, authentication).orElse(null));
        return roomAssembler.toRoomModel(RoomResponse, authentication);
    }

    public void deleteRoomType(Long id) {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomRepository.deleteById(id);
    }

    private Optional<EntityModel<ReservationResponse>> getCurrentReservationModel(Room room, Authentication authentication) {
        return room.getReservations().stream()
                .filter(reservation -> reservation.getCheckOut().after(new Date()))
                .findFirst()
                .map(reservation -> reservationService.getReservation(reservation.getId(), authentication));
    }
}
