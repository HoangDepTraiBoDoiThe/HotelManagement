package com.example.hotelmanagement.helper;

import com.example.hotelmanagement.model.Reservation;
import com.example.hotelmanagement.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class MyPermissionEvaluator implements PermissionEvaluator {
    private final ReservationService reservationService;
    
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    boolean checkPermission(Authentication authentication, String permission) {
        return false;
    }

    public boolean hasOwnerPermission(Authentication authentication, String objectType, long objectId, String permission) {
        String authUserName = authentication.getName();
        
        if (Reservation.class.getName().equals(objectType)) {
            Reservation reservation = reservationService.getReservation_entity(objectId);
            if (reservation != null && reservation.getOwner().getName().equals(authUserName)) {
                return true;
            }
        }
        return false;
    }
}
