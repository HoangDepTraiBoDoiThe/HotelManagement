package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppPhotos {

    @Id
    @GeneratedValue
    private long id;

    @Lob
    @Column(nullable = false)
    private Blob image;
    private String description;
    private String name;
    
    @ManyToMany(mappedBy = "roomImage")
    private List<Room> rooms = new ArrayList<>();    
    @ManyToMany(mappedBy = "roomTypeImages")
    private List<RoomType> roomTypes = new ArrayList<>();
}
