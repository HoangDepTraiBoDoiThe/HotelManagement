package com.example.hotelmanagement.helper;

import com.example.hotelmanagement.constants.ApplicationRole;
import com.example.hotelmanagement.controller.assembler.BillAssembler;
import com.example.hotelmanagement.dto.response.BillResponse;
import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.dto.response.RoomResponse;
import com.example.hotelmanagement.dto.response.UserResponse;
import com.example.hotelmanagement.dto.response.roomUtility.UtilityResponse_Minimal;
import com.example.hotelmanagement.model.Bill;
import com.example.hotelmanagement.model.Reservation;
import com.example.hotelmanagement.service.ReservationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StaticHelper {
    public static Blob toBlob(String iamgeString) throws SQLException {
        if (iamgeString.isBlank()) return null;
        try {
            byte[] multiPartFileBytes = iamgeString.getBytes();
            return new SerialBlob(multiPartFileBytes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ApplicationRole> getAllRolesToAdd() {
        List<ApplicationRole> applicationRoles = new ArrayList<>();
        applicationRoles.add(ApplicationRole.GUESS);
        applicationRoles.add(ApplicationRole.ADMIN);
        applicationRoles.add(ApplicationRole.MANAGER);
        return  applicationRoles;
    }

    public List<String> getUserApplicationRole(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }
}
