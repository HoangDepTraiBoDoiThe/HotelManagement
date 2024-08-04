package com.example.hotelmanagement.helper;

import org.springframework.context.annotation.Configuration;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

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
}
