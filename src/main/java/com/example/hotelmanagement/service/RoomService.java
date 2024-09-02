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
        List<RoomResponse_Basic> responses = serviceHelper.makeRoomResponseList(RoomResponse_Basic.class, rooms);
        return serviceHelper.makeRoomResponse_CollectionModel(responses, authentication);
    }

    public EntityModel<RoomResponse_Full> getRoomById(Long id, Authentication authentication) {
        Room room = roomRepository.findAllWithReservations(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Can not find any room with this room id [%d]", id)));
        RoomResponse_Full response = serviceHelper.makeRoomResponseInstance(RoomResponse_Full.class, room);
        return serviceHelper.makeRoomResponse_EntityModel(response, authentication);
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

        RoomResponse_Full response = serviceHelper.makeRoomResponseInstance(RoomResponse_Full.class, newCreatedRoom);
        return serviceHelper.makeRoomResponse_EntityModel(response, authentication);
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

        RoomResponse_Full response = serviceHelper.makeRoomResponseInstance(RoomResponse_Full.class, updatedRoom);
        return serviceHelper.makeRoomResponse_EntityModel(response, authentication);    }

    public void deleteRoomType(Long id) {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomRepository.deleteById(id);
    }
}
