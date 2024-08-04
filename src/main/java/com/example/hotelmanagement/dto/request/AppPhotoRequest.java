package com.example.hotelmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppPhotoRequest {
    private String image;
    private String description;
    private String name;
}
