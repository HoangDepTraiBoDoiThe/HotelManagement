package com.example.hotelmanagement.model;

import com.example.hotelmanagement.model.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoomImage {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String imageUrl;
    private String description;
    private String name;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private List<Room> rooms = new ArrayList<>();    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private List<Room> rooms = new ArrayList<>();
}
