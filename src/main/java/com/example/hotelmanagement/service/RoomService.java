package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.room.RoomResponse_Basic;
import com.example.hotelmanagement.dto.response.room.RoomResponse_Full;
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

@Transactional
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeService roomTypeService;
    private final UtilityService utilityService;
    private final ServiceHelper serviceHelper;

    public CollectionModel<EntityModel<RoomResponse_Basic>> getAllRooms(Authentication authentication) {
        List<Room> rooms = roomRepository.findAll();
        CollectionModel<EntityModel<RoomResponse_Basic>> entityModelCollectionModels = rooms.stream().map(room -> serviceHelper.makeRoomResponse(RoomResponse_Basic.class, room, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
        
        return entityModelCollectionModels;
    }

    public EntityModel<RoomResponse_Full> getRoomById(Long id, Authentication authentication) {
        Room room = roomRepository.findWithRoomTypesById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Can not find any room with this room id [%d]", id)));
        return serviceHelper.makeRoomResponse(RoomResponse_Full.class, room, authentication);
    }
    
    public Room getRoomById_entity(Long id, Authentication authentication, boolean shouldThrowIfNull) {
        Room room = roomRepository.findWithRoomTypesById(id).orElse(null);
        if (room == null && shouldThrowIfNull) throw new ResourceNotFoundException(String.format("Can not find any room with this room id [%d]", id));
        return room;
    }
    
    @Transactional
    public EntityModel<RoomResponse_Full> createRoom(RoomRequest roomRequest, Authentication authentication) {
        Room newRoom = new Room(roomRequest.roomName(), roomRequest.roomDescription(), roomRequest.roomBasePrice(), roomRequest.roomFloor(), roomRequest.roomNumber(), RoomStatus.ROOM_AVAILABLE.name());
        Set<RoomType> roomTypes = roomRequest.roomTypeIds().stream().map(aLong -> roomTypeService.getRoomTypeById_entity(aLong, authentication)).collect(Collectors.toSet());
        Set<Utility> utilities = roomRequest.utilityIds().stream().map(aLong -> utilityService.getUtilityById_entity(aLong, authentication, false)).collect(Collectors.toSet());
        newRoom.setRoomTypes(roomTypes);
        newRoom.setRoomUtilities(utilities);
        Room newCreatedRoom = roomRepository.save(newRoom);

        return serviceHelper.makeRoomResponse(RoomResponse_Full.class, newCreatedRoom, authentication);
    }

    @Transactional
    public EntityModel<RoomResponse_Full> updateRoomType(long id, RoomRequest roomRequest, Authentication authentication) {
        Room room = roomRepository.findWithRoomTypesById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        room.setRoomName(roomRequest.roomName());
        room.setRoomDescription(roomRequest.roomDescription());
        room.setRoomBasePrice(roomRequest.roomBasePrice());
        Set<RoomType> roomTypes = roomRequest.roomTypeIds().stream().map(aLong -> roomTypeService.getRoomTypeById_entity(aLong, authentication)).collect(Collectors.toSet());
        room.setRoomTypes(roomTypes);
        Room updatedRoom = roomRepository.save(room);
        
        return serviceHelper.makeRoomResponse(RoomResponse_Full.class, updatedRoom, authentication);
    }

    public void deleteRoomType(Long id) {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomRepository.deleteById(id);
    }
}
