package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.RoomAssembler;
import com.example.hotelmanagement.controller.assembler.RoomTypeAssembler;
import com.example.hotelmanagement.controller.assembler.UtilityAssembler;
import com.example.hotelmanagement.dto.request.UtilityRequest;
import com.example.hotelmanagement.dto.response.ResponseBase;
import com.example.hotelmanagement.dto.response.RoomResponse;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Full;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.UtilityRepository;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilityService {
    private final UtilityRepository utilityRepository;
    private final RoomAssembler roomAssembler;
    private final RoomTypeAssembler roomTypeAssembler;
    private final UtilityAssembler utilityAssembler;
    
    public CollectionModel<EntityModel<UtilityResponse_Full>> getAllUtility(Authentication authentication) {
        List<Utility> utilities = utilityRepository.findAll();
        
        List<UtilityResponse_Full> utilityResponsesFull = utilities.stream().map(utility -> makeUtilityResponseFull(utility, authentication)).toList();
        return utilityAssembler.toCollectionModel(utilityResponsesFull, authentication);
    }

    public EntityModel<UtilityResponse_Full> getUtilityById(long utilityId, Authentication authentication) {
        Utility utility = utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException("Utility not found"));
        return utilityAssembler.toModel(makeUtilityResponseFull(utility, authentication), authentication);
    }

    public List<Utility> getAllByIds_entity(List<Long> longs, Authentication authentication) {
        return longs.stream().map(aLong -> getUtilityById_entity(aLong, authentication)).toList();
    }

    public Utility getUtilityById_entity(long utilityId, Authentication authentication) {
        return utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException(String.format("Utility with id %d not found", utilityId)));
    }
    
    public EntityModel<UtilityResponse_Full> createUtility(Utility utility, Authentication authentication) {
        Utility newCreatedUtility = utilityRepository.save(utility);
        return utilityAssembler.toModel(makeUtilityResponseFull(newCreatedUtility, authentication), authentication);
    }
    
    public EntityModel<UtilityResponse_Full> updateUtility(long utilityId, UtilityRequest newUtilityData, Authentication authentication) {
        Utility existingUtility = utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException("Utility not found"));

        existingUtility.setUtilityName(newUtilityData.getUtilityName());
        existingUtility.setUtilityBasePrice(newUtilityData.getBaseUtilityPrice());
        existingUtility.setUtilityStatus(newUtilityData.isStatus());
        existingUtility.setUtilityDescription(newUtilityData.getUtilityDescription());

        Utility updatedUtility = utilityRepository.save(existingUtility);
        return utilityAssembler.toModel(makeUtilityResponseFull(updatedUtility, authentication), authentication);
    }
    
    public void deleteUtility(long utilityId) {
        Utility utility = utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException("Utility not found"));
        utilityRepository.delete(utility);
    }


    public UtilityResponse_Full makeUtilityResponseFull(Utility utility, Authentication authentication) {
        List<ResponseBase> roomResponseBases = utility.getRooms().stream().map(room -> new ResponseBase(room.getId())).toList();
        CollectionModel<EntityModel<ResponseBase>> roomCollectionModel = roomAssembler.toCollectionModel(roomResponseBases, authentication);

        List<ResponseBase> roomTypeResponseBases = utility.getRoomTypes().stream().map(room -> new ResponseBase(room.getId())).toList();
        CollectionModel<EntityModel<ResponseBase>> roomTypeCollectionModel = roomTypeAssembler.toCollectionModel(roomTypeResponseBases, authentication);

        return new UtilityResponse_Full(utility, roomCollectionModel, roomTypeCollectionModel, null);
    }

}
