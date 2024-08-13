package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.RoomTypeRequest;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.RoomType;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.RoomTypeRepository;
import com.example.hotelmanagement.model.repository.UtilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;
    private final UtilityRepository utilityRepository;
    
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }
    
    public RoomType getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
    }
    
    @Transactional
    public RoomType createRoomType(RoomTypeRequest roomTypeRequest) {
        RoomType newRoomType = new RoomType(roomTypeRequest.roomTypeName(), roomTypeRequest.description(), roomTypeRequest.roomCapability(), roomTypeRequest.basePrice());
        Set<Utility> utilities = new HashSet<>(utilityRepository.findAllById(roomTypeRequest.utilityIds()));
        newRoomType.setRoomTypeUtilities(utilities);
        return roomTypeRepository.save(newRoomType);
    }

    @Transactional
    public RoomType updateRoomType(long id, RoomTypeRequest roomTypeRequest) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        
        roomType.setTypeName(roomTypeRequest.roomTypeName());
        roomType.setDescription(roomTypeRequest.description());
        roomType.setBasePrice(roomTypeRequest.basePrice());
        roomType.setCapacity(roomTypeRequest.roomCapability());
        Set<Utility> utilities = new HashSet<>(utilityRepository.findAllById(roomTypeRequest.utilityIds()));
        roomType.setRoomTypeUtilities(utilities);
        // todo: room type images missing
        return roomTypeRepository.save(roomType);
    }
    
    public void deleteRoomType(Long id) {
        roomTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
        roomTypeRepository.deleteById(id);
    }
}
