package com.example.hotelmanagement.service;

import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.Utility;
import com.example.hotelmanagement.model.repository.UtilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
