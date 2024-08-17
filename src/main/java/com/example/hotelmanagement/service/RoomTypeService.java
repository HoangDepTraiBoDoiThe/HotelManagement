package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.RoomTypeAssembler;
import com.example.hotelmanagement.dto.request.RoomTypeRequest;
import com.example.hotelmanagement.dto.response.RoomTypeResponse;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.helper.ServiceHelper;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.RoomTypeRepository;
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
    private final UtilityService utilityService;
    private final ServiceHelper serviceHelper;

    public CollectionModel<EntityModel<RoomTypeResponse>> getAllRoomTypes(Authentication authentication) {
        List<RoomType> roomTypes = roomTypeRepository.findAll();
        return roomTypes.stream().map(roomType -> serviceHelper.makeRoomTypeResponse(roomType, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }
    
    public EntityModel<RoomTypeResponse> getRoomTypeById(Long id, Authentication authentication) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        return serviceHelper.makeRoomTypeResponse(roomType, authentication);
    }   
    public RoomType getRoomTypeById_entity(Long id, Authentication authentication) {
        return roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
    }

    @Transactional
    public EntityModel<RoomTypeResponse> createRoomType(RoomTypeRequest roomTypeRequest, Authentication authentication) {
        RoomType newRoomType = new RoomType(roomTypeRequest.roomTypeName(), roomTypeRequest.description(), roomTypeRequest.roomCapability(), roomTypeRequest.basePrice());
        Set<Utility> utilities = roomTypeRequest.utilityIds().stream().map((Long utilityId) -> utilityService.getUtilityById_entity(utilityId, authentication, true)).collect(Collectors.toSet());
        newRoomType.setRoomTypeUtilities(utilities);

        RoomType newCreatedRoomType = roomTypeRepository.save(newRoomType);
        return serviceHelper.makeRoomTypeResponse(newCreatedRoomType, authentication);
    }

    @Transactional
    public EntityModel<RoomTypeResponse> updateRoomType(long id, RoomTypeRequest roomTypeRequest, Authentication authentication) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        
        roomType.setTypeName(roomTypeRequest.roomTypeName());
        roomType.setDescription(roomTypeRequest.description());
        roomType.setBasePrice(roomTypeRequest.basePrice());
        roomType.setCapacity(roomTypeRequest.roomCapability());
        Set<Utility> utilities = new HashSet<>(utilityService.getAllByIds_entity(roomTypeRequest.utilityIds(), authentication, false));
        roomType.setRoomTypeUtilities(utilities);
        RoomType updatedRoomType = roomTypeRepository.save(roomType);
        return serviceHelper.makeRoomTypeResponse(updatedRoomType, authentication);
    }
    
    public void deleteRoomType(Long id) {
        roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        roomTypeRepository.deleteById(id);
    }

}
