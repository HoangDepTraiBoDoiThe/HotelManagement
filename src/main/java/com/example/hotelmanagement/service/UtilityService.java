package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.RoomAssembler;
import com.example.hotelmanagement.controller.assembler.RoomTypeAssembler;
import com.example.hotelmanagement.controller.assembler.UtilityAssembler;
import com.example.hotelmanagement.dto.request.UtilityRequest;
import com.example.hotelmanagement.dto.response.ResponseBase;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Basic;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Full;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Minimal;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.helper.ServiceHelper;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.UtilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilityService {
    private final UtilityRepository utilityRepository;
    private final ServiceHelper serviceHelper;
    
    public CollectionModel<EntityModel<UtilityResponse_Full>> getAllUtility(Authentication authentication) {
        List<Utility> utilities = utilityRepository.findAll();
        
        CollectionModel<EntityModel<UtilityResponse_Full>> utilityResponsesFull = utilities.stream().map(utility -> serviceHelper.makeUtilityResponse(UtilityResponse_Full.class, utility, authentication)).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
        return utilityResponsesFull;
    }

    public EntityModel<UtilityResponse_Full> getUtilityById(long utilityId, Authentication authentication) {
        Utility utility = utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException("Utility not found"));
        return serviceHelper.makeUtilityResponse(UtilityResponse_Full.class, utility, authentication);
    }

    public List<Utility> getAllByIds_entity(List<Long> longs, Authentication authentication, boolean shouldThrowIfNull) {
        return longs.stream().map(aLong -> getUtilityById_entity(aLong, authentication, shouldThrowIfNull)).toList();
    }

    public Utility getUtilityById_entity(long utilityId, Authentication authentication, boolean shouldThrowIfNull) {
        Utility utility = utilityRepository.findById(utilityId).orElse(null);
        if (utility == null && shouldThrowIfNull) throw new ResourceNotFoundException(String.format("Utility with id %d not found", utilityId));
        return utility;
    }
    
    public EntityModel<UtilityResponse_Basic> createUtility(UtilityRequest utilityRequest, Authentication authentication) {
        Utility newUtility = utilityRequest.toModel();
        Utility newCreatedUtility = utilityRepository.save(newUtility);
        return serviceHelper.makeUtilityResponse(UtilityResponse_Basic.class, newCreatedUtility, authentication);
    }
    
    public EntityModel<UtilityResponse_Full> updateUtility(long utilityId, UtilityRequest newUtilityData, Authentication authentication) {
        Utility existingUtility = utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException("Utility not found"));

        existingUtility.setUtilityName(newUtilityData.getUtilityName());
        existingUtility.setUtilityBasePrice(newUtilityData.getBaseUtilityPrice());
        existingUtility.setUtilityStatus(newUtilityData.isStatus());
        existingUtility.setUtilityDescription(newUtilityData.getUtilityDescription());

        Utility updatedUtility = utilityRepository.save(existingUtility);
        return serviceHelper.makeUtilityResponse(UtilityResponse_Full.class, updatedUtility, authentication);
    }
    
    public void deleteUtility(long utilityId) {
        Utility utility = utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException("Utility not found"));
        utilityRepository.delete(utility);
    }

}
