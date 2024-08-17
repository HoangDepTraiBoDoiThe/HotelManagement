package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.RoomTypeRequest;
import com.example.hotelmanagement.dto.response.room.roomType.RoomTypeResponse_Basic;
import com.example.hotelmanagement.dto.response.room.roomType.RoomTypeResponse_Full;
import com.example.hotelmanagement.dto.response.room.roomType.RoomTypeResponse_Minimal;
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

    public CollectionModel<EntityModel<RoomTypeResponse_Basic>> getAllRoomTypes(Authentication authentication) {
        List<RoomType> roomTypes = roomTypeRepository.findAll();
        return roomTypes.stream().map(roomType -> serviceHelper.makeRoomTypeResponse(RoomTypeResponse_Basic.class, roomType, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }
    
    public EntityModel<RoomTypeResponse_Full> getRoomTypeById(Long id, Authentication authentication) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        return serviceHelper.makeRoomTypeResponse(RoomTypeResponse_Full.class, roomType, authentication);
    }   
    public RoomType getRoomTypeById_entity(Long id, Authentication authentication) {
        return roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
    }

    @Transactional
    public EntityModel<RoomTypeResponse_Minimal> createRoomType(RoomTypeRequest roomTypeRequest, Authentication authentication) {
        RoomType newRoomType = new RoomType(roomTypeRequest.roomTypeName(), roomTypeRequest.description(), roomTypeRequest.roomCapability(), roomTypeRequest.basePrice());
        Set<Utility> utilities = roomTypeRequest.utilityIds().stream().map((Long utilityId) -> utilityService.getUtilityById_entity(utilityId, authentication, true)).collect(Collectors.toSet());
        newRoomType.setRoomTypeUtilities(utilities);

        RoomType newCreatedRoomType = roomTypeRepository.save(newRoomType);
        return serviceHelper.makeRoomTypeResponse(RoomTypeResponse_Minimal.class, newCreatedRoomType, authentication);
    }

    @Transactional
    public EntityModel<RoomTypeResponse_Full> updateRoomType(long id, RoomTypeRequest roomTypeRequest, Authentication authentication) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        
        roomType.setTypeName(roomTypeRequest.roomTypeName());
        roomType.setDescription(roomTypeRequest.description());
        roomType.setBasePrice(roomTypeRequest.basePrice());
        roomType.setCapacity(roomTypeRequest.roomCapability());
        Set<Utility> utilities = new HashSet<>(utilityService.getAllByIds_entity(roomTypeRequest.utilityIds(), authentication, false));
        roomType.setRoomTypeUtilities(utilities);
        RoomType updatedRoomType = roomTypeRepository.save(roomType);
        return serviceHelper.makeRoomTypeResponse(RoomTypeResponse_Full.class, updatedRoomType, authentication);
    }
    
    public void deleteRoomType(Long id) {
        roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        roomTypeRepository.deleteById(id);
    }

}
