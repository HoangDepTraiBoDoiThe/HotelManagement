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
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "room_images", joinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private List<Room> rooms = new ArrayList<>();    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "room_type_images", joinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomType_id", referencedColumnName = "id"))
    private List<RoomType> roomTypes = new ArrayList<>();
}
