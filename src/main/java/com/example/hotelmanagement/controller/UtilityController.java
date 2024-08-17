package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.request.UtilityRequest;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Basic;
import com.example.hotelmanagement.service.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/utilities")
public class UtilityController {
    private final UtilityService utilityService;

    @PostMapping("/create")
    public ResponseEntity<EntityModel<UtilityResponse_Basic>> createUtility(@RequestBody @Validated UtilityRequest utilityRequest, Authentication authentication) {
        return ResponseEntity.ok(utilityService.createUtility(utilityRequest, authentication));
    }
}
