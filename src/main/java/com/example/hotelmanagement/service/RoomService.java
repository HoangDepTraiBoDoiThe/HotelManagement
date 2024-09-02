package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.RoomController;
import com.example.hotelmanagement.dto.request.RoomRequest;
import com.example.hotelmanagement.dto.response.room.RoomResponse_Basic;
import com.example.hotelmanagement.dto.response.room.RoomResponse_Full;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.constants.RoomStatus;
import com.example.hotelmanagement.helper.CacheHelper;
import com.example.hotelmanagement.helper.ServiceHelper;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Transactional
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeService roomTypeService;
    private final UtilityService utilityService;
    private final ServiceHelper serviceHelper;
    private final CacheHelper cacheHelper;

    private final String ROOM_LIST_CACHE = "roomListCache";
    private final String ROOM_CACHE = "roomCache";
    private final String ROOM_PAGINATION_CACHE_PREFIX = "roomPaginationCache";
    private final String TOP_SELLER_ROOM_PAGINATION_CACHE_PREFIX = "topSeller";
    private final String ROOM_OF_CATEGORY_PAGINATION_CACHE_PREFIX = "roomsOfCategory";
    private final Integer PAGE_RANGE = 10;
    private final Integer PAGE_SIZE = 10;

    public CollectionModel<EntityModel<RoomResponse_Basic>> getRooms(int page, Authentication authentication) {
        return cacheHelper.getPaginationCache(ROOM_OF_CATEGORY_PAGINATION_CACHE_PREFIX, RoomResponse_Basic.class, page, roomRepository, repo -> {
            List<Room> rooms = roomRepository.findAllByFloorNumber(PageRequest.of(page, PAGE_SIZE)).stream().toList();
            CollectionModel<EntityModel<RoomResponse_Basic>> entityModels = rooms.stream().map(room -> serviceHelper.makeRoomResponse(RoomResponse_Basic.class, room, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
            return serviceHelper.addLinksToPaginationResponse(entityModels, page, p -> methodOn(RoomController.class).getRoomRooms(p, authentication));
        });
    }

    public EntityModel<RoomResponse_Full> getRoomById(Long id, Authentication authentication) {
        return cacheHelper.getCache(ROOM_CACHE, RoomResponse_Full.class, id, roomRepository, repo -> {
            Room room = roomRepository.findAllWithReservations(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Can not find any room with this room id [%d]", id)));
            return serviceHelper.makeRoomResponse(RoomResponse_Full.class, room, authentication);
        }, 30);
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
        EntityModel<RoomResponse_Full> entityModel = serviceHelper.makeRoomResponse(RoomResponse_Full.class, updatedRoom, authentication);

        cacheHelper.updateCache(entityModel, ROOM_CACHE);
        cacheHelper.updatePaginationCache(entityModel, PAGE_RANGE, ROOM_PAGINATION_CACHE_PREFIX);

        return entityModel;
    }

    public void deleteRoomType(Long id) {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomRepository.deleteById(id);
        cacheHelper.deleteCaches(ROOM_CACHE, id, ROOM_LIST_CACHE);
        cacheHelper.deletePaginationCache(PAGE_RANGE, ROOM_PAGINATION_CACHE_PREFIX);
    }
}
