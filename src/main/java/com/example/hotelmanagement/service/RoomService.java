package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.ReservationAssembler;
import com.example.hotelmanagement.controller.assembler.RoomTypeAssembler;
import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.dto.response.RoomResponseAdmin;
import com.example.hotelmanagement.dto.response.RoomResponseGuess;
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
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final UtilityRepository utilityRepository;
    private final RoomTypeAssembler roomTypeAssembler;
    private final ReservationAssembler reservationAssembler;

    public List<RoomResponseAdmin> getAllRooms_Admin() {
        List<Room> rooms = roomRepository.findAllWithReservations();
        List<RoomResponseAdmin> roomResponseAdmins = rooms.stream().map(room -> new RoomResponseAdmin(room,
                roomTypeAssembler.toCollectionModel(room.getRoomTypes()),
                null,
                getCurrentReservationModel(room).orElse(null),
                reservationAssembler.toCollectionModel(room.getReservations())
        )).collect(Collectors.toList());
        
        return roomResponseAdmins;
    }
    public List<RoomResponseGuess> getAllRooms_Guess() {
        List<Room> rooms = roomRepository.findAllWithReservations();
        List<RoomResponseGuess> roomResponsesGuess = rooms.stream().map(room -> new RoomResponseGuess(room, roomTypeAssembler.toCollectionModel(room.getRoomTypes()), null)).collect(Collectors.toList());
        
        return roomResponsesGuess;
    }

    public RoomResponseAdmin getRoomById_Admin(Long id) {
        Room room = roomRepository.findWithReservationsById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponseModels = roomTypeAssembler.toCollectionModel(room.getRoomTypes());
        CollectionModel<EntityModel<ReservationResponse>> reservationHistoryResponseModels = reservationAssembler.toCollectionModel(room.getReservations());

        RoomResponseAdmin roomResponseAdmin = new RoomResponseAdmin(room, roomTypeResponseModels, null, getCurrentReservationModel(room).orElse(null), reservationHistoryResponseModels);
        return roomResponseAdmin;
    }
    
    public RoomResponseGuess getRoomById_Guess(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponseModels = roomTypeAssembler.toCollectionModel(room.getRoomTypes());
        RoomResponseGuess roomResponseAGuess = new RoomResponseGuess(room, roomTypeResponseModels, null);
        return roomResponseAGuess;
    }

    @Transactional
    public RoomResponseAdmin createRoom(RoomRequest roomRequest) {
        Room newRoom = new Room(roomRequest.roomName(), roomRequest.roomDescription(), roomRequest.roomBasePrice(), roomRequest.roomFloor(), roomRequest.roomNumber(), RoomStatus.ROOM_AVAILABLE.name());
        Set<RoomType> roomTypes = new HashSet<>(roomTypeRepository.findAllById(roomRequest.roomTypeIds()));
        Set<Utility> utilities = new HashSet<>(utilityRepository.findAllById(roomRequest.utilityIds()));
        newRoom.setRoomTypes(roomTypes);
        newRoom.setRoomUtilities(utilities);
        Room newCreatedRoom = roomRepository.save(newRoom);
        return new RoomResponseAdmin(newCreatedRoom, roomTypeAssembler.toCollectionModel(roomTypes), null, null, null);
    }

    @Transactional
    public RoomResponseAdmin updateRoomType(long id, RoomRequest roomRequest) {
        Room room = roomRepository.findWithReservationsById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        room.setRoomName(roomRequest.roomName());
        room.setRoomDescription(roomRequest.roomDescription());
        room.setRoomBasePrice(roomRequest.roomBasePrice());
        Set<RoomType> roomTypes = new HashSet<>(roomTypeRepository.findAllById(roomRequest.roomTypeIds()));
        room.setRoomTypes(roomTypes);
        // todo: room type images missing
        
        Room updatedRoom = roomRepository.save(room);
        CollectionModel<EntityModel<RoomTypeResponse>> roomTypeResponseModels = roomTypeAssembler.toCollectionModel(updatedRoom.getRoomTypes());
        CollectionModel<EntityModel<ReservationResponse>> reservationHistoryResponseModels = reservationAssembler.toCollectionModel(updatedRoom.getReservations());
        RoomResponseAdmin roomResponseAdmin = new RoomResponseAdmin(updatedRoom, roomTypeResponseModels, null, getCurrentReservationModel(updatedRoom).orElse(null), reservationHistoryResponseModels);
        return roomResponseAdmin;
    }

    public void deleteRoomType(Long id) {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomRepository.deleteById(id);
    }

    private Optional<EntityModel<ReservationResponse>> getCurrentReservationModel(Room room) {
        return room.getReservations().stream()
                .filter(reservation -> reservation.getCheckOut().after(new Date()))
                .findFirst()
                .map(reservationAssembler::toModel);
    }
}
