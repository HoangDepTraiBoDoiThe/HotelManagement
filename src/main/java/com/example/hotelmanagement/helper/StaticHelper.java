package com.example.hotelmanagement.helper;

import com.example.hotelmanagement.constants.ApplicationRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public static Set<String> extractAllRoles(Authentication authentication) {
        if (authentication == null) return new HashSet<>();
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    public List<String> getUserApplicationRole(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now(ZoneId.systemDefault());
    }
}
