package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dto.request.UtilityRequest;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.UtilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilityService {
    private final UtilityRepository utilityRepository;
    
    public List<Utility> getAllUtility() {
        return utilityRepository.findAll();
    }

    public Utility getUtilityById(long utilityId) {
        return utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException("Utility not found"));
    }
    
    public Utility createUtility(Utility utility) {
        return utilityRepository.save(utility);
    }
    
    public Utility updateUtility(long utilityId, @Validated UtilityRequest newUtilityData) {
        Utility existingUtility = utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException("Utility not found"));

        existingUtility.setUtilityName(newUtilityData.utilityName());
        existingUtility.setUtilityBasePrice(newUtilityData.baseUtilityPrice());
        existingUtility.setUtilityStatus(newUtilityData.status());
        existingUtility.setUtilityDescription(newUtilityData.utilityDescription());
        
        return utilityRepository.save(existingUtility);
    }
    
    public void deleteUtility(long utilityId) {
        Utility utility = utilityRepository.findById(utilityId).orElseThrow(() -> new ResourceNotFoundException("Utility not found"));
        utilityRepository.delete(utility);
    }
}
