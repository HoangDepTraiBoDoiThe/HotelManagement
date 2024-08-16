package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.RoomTypeAssembler;
import com.example.hotelmanagement.dto.request.RoomTypeRequest;
import com.example.hotelmanagement.dto.response.RoomTypeResponse;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.RoomTypeRepository;
import com.example.hotelmanagement.model.repository.UtilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomTypeAssembler roomTypeAssembler;
    private final UtilityService utilityService;
    
    public CollectionModel<EntityModel<RoomTypeResponse>> getAllRoomTypes(Authentication authentication) {
        List<RoomType> roomTypes = roomTypeRepository.findAll();
        List<RoomTypeResponse> roomTypeResponses = roomTypes.stream().map(roomType -> makeRoomTypeResponse(roomType, authentication)).toList();
        return roomTypeAssembler.toCollectionModel(roomTypeResponses, authentication);
    }
    
    public EntityModel<RoomTypeResponse> getRoomTypeById(Long id, Authentication authentication) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));

        RoomTypeResponse roomTypeResponse = makeRoomTypeResponse(roomType, authentication);
        return roomTypeAssembler.toModel(roomTypeResponse, authentication);
    }   
    public RoomType getRoomTypeById_entity(Long id, Authentication authentication) {
        return roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
    }

    @Transactional
    public EntityModel<RoomTypeResponse> createRoomType(RoomTypeRequest roomTypeRequest, Authentication authentication) {
        RoomType newRoomType = new RoomType(roomTypeRequest.roomTypeName(), roomTypeRequest.description(), roomTypeRequest.roomCapability(), roomTypeRequest.basePrice());
        Set<Utility> utilities = roomTypeRequest.utilityIds().stream().map((Long utilityId) -> utilityService.getUtilityById_entity(utilityId, authentication)).collect(Collectors.toSet());
        newRoomType.setRoomTypeUtilities(utilities);

        RoomType newCreatedRoomType = roomTypeRepository.save(newRoomType);
        RoomTypeResponse roomTypeResponse = makeRoomTypeResponse(newCreatedRoomType, authentication);
        return roomTypeAssembler.toModel(roomTypeResponse, authentication);
    }

    @Transactional
    public EntityModel<RoomTypeResponse> updateRoomType(long id, RoomTypeRequest roomTypeRequest, Authentication authentication) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        
        roomType.setTypeName(roomTypeRequest.roomTypeName());
        roomType.setDescription(roomTypeRequest.description());
        roomType.setBasePrice(roomTypeRequest.basePrice());
        roomType.setCapacity(roomTypeRequest.roomCapability());
        Set<Utility> utilities = new HashSet<>(utilityService.getAllByIds_entity(roomTypeRequest.utilityIds(), authentication));
        roomType.setRoomTypeUtilities(utilities);
        RoomType updatedRoomType = roomTypeRepository.save(roomType);
        return roomTypeAssembler.toModel(makeRoomTypeResponse(updatedRoomType, authentication), authentication);
    }
    
    public void deleteRoomType(Long id) {
        roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        roomTypeRepository.deleteById(id);
    }

    public RoomTypeResponse makeRoomTypeResponse(RoomType roomType, Authentication authentication) {
        RoomTypeResponse roomTypeResponse = new RoomTypeResponse(roomType, null);
        return roomTypeResponse;
    }
}
