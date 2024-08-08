package com.example.hotelmanagement.model;

import com.example.hotelmanagement.helper.RoomStatus;
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
public class Room {
    
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private Number floorNumber;
    @Column(nullable = false)
    private String roomNumber;
    private RoomStatus roomStatus = RoomStatus.ROOM_OUT_OF_ORDER;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private List<AppPhotos> roomImage;
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "Room_Reservation", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "reservatino_is", referencedColumnName = "id"))
    private List<Reservation> reservations = new ArrayList<>();
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "Room_Reservation", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomType_id", referencedColumnName = "id"))
    private List<RoomType> roomTypes = new ArrayList<>();
}
