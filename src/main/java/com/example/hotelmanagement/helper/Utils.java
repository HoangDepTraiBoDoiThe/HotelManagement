package com.example.hotelmanagement.helper;

import com.example.hotelmanagement.constants.ApplicationRole;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Utils {
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
}
