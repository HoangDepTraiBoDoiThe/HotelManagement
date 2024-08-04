package com.example.hotelmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppPhotoResponse {
    private long id;
    private String image;
    private String description;
    private String name;
}
