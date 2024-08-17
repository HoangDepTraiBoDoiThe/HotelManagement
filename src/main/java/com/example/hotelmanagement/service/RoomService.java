package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.RoomResponse;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.constants.RoomStatus;
import com.example.hotelmanagement.helper.ServiceHelper;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.RoomRepository;
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
    private final RoomTypeService roomTypeService;
    private final UtilityService utilityService;
    private final ServiceHelper serviceHelper;

    public CollectionModel<EntityModel<RoomResponse>> getAllRooms(Authentication authentication) {
        List<Room> rooms = roomRepository.findAllWithReservations();
        CollectionModel<EntityModel<RoomResponse>> entityModelCollectionModels = rooms.stream().map(room -> serviceHelper.makeRoomResponse(room, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
        
        return entityModelCollectionModels;
    }

    public EntityModel<RoomResponse> getRoomById(Long id, Authentication authentication) {
        Room room = roomRepository.findWithReservationsById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Can not find any room with this room id [%d]", id)));
        return serviceHelper.makeRoomResponse(room, authentication);
    }
    
    public Room getRoomById_entity(Long id, Authentication authentication, boolean shouldThrowIfNull) {
        Room room = roomRepository.findWithReservationsById(id).orElse(null);
        if (room == null && shouldThrowIfNull) throw new ResourceNotFoundException(String.format("Can not find any room with this room id [%d]", id));
        return room;
    }
    
    @Transactional
    public EntityModel<RoomResponse> createRoom(RoomRequest roomRequest, Authentication authentication) {
        Room newRoom = new Room(roomRequest.roomName(), roomRequest.roomDescription(), roomRequest.roomBasePrice(), roomRequest.roomFloor(), roomRequest.roomNumber(), RoomStatus.ROOM_AVAILABLE.name());
        Set<RoomType> roomTypes = roomRequest.roomTypeIds().stream().map(aLong -> roomTypeService.getRoomTypeById_entity(aLong, authentication)).collect(Collectors.toSet());
        Set<Utility> utilities = roomRequest.utilityIds().stream().map(aLong -> utilityService.getUtilityById_entity(aLong, authentication, false)).collect(Collectors.toSet());
        newRoom.setRoomTypes(roomTypes);
        newRoom.setRoomUtilities(utilities);
        Room newCreatedRoom = roomRepository.save(newRoom);

        return serviceHelper.makeRoomResponse(newCreatedRoom, authentication);
    }

    @Transactional
    public EntityModel<RoomResponse> updateRoomType(long id, RoomRequest roomRequest, Authentication authentication) {
        Room room = roomRepository.findWithReservationsById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        room.setRoomName(roomRequest.roomName());
        room.setRoomDescription(roomRequest.roomDescription());
        room.setRoomBasePrice(roomRequest.roomBasePrice());
        Set<RoomType> roomTypes = roomRequest.roomTypeIds().stream().map(aLong -> roomTypeService.getRoomTypeById_entity(aLong, authentication)).collect(Collectors.toSet());
        room.setRoomTypes(roomTypes);
        Room updatedRoom = roomRepository.save(room);
        
        return serviceHelper.makeRoomResponse(updatedRoom, authentication);
    }

    public void deleteRoomType(Long id) {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomRepository.deleteById(id);
    }
}
