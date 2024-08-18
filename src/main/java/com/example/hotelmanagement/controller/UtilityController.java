package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.request.UtilityRequest;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Basic;
import com.example.hotelmanagement.service.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/utilities")
public class UtilityController {
    private final UtilityService utilityService;

    @GetMapping
    public ResponseEntity<?> getAllUtilities(Authentication authentication) {
        return ResponseEntity.ok(utilityService.getAllUtility(authentication));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUtilityById(@PathVariable long id, Authentication authentication) {
        return ResponseEntity.ok(utilityService.getUtilityById(id, authentication));
    }
    
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateUtility(@PathVariable long id, @RequestBody @Validated UtilityRequest utilityRequest, Authentication authentication) {
        return ResponseEntity.ok(utilityService.updateUtility(id, utilityRequest, authentication));
    }
    
        
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUtility(@PathVariable long id) {
        utilityService.deleteUtility(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/create")
    public ResponseEntity<EntityModel<UtilityResponse_Basic>> createUtility(@RequestBody @Validated UtilityRequest utilityRequest, Authentication authentication) {
        return ResponseEntity.ok(utilityService.createUtility(utilityRequest, authentication));
    }
}
