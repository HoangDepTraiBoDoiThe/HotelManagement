package com.example.hotelmanagement.model;

import com.example.hotelmanagement.constants.RoomStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String roomName;
    
    private String roomDescription;
    
    @Column(nullable = false)
    private BigDecimal roomBasePrice;    
    
    @Column(nullable = false)
    private Number floorNumber;
    
    @Column(nullable = false)
    private Number roomNumber;

    @Column(nullable = false)
    private String roomStatus = RoomStatus.ROOM_OUT_OF_ORDER.toString();

    public Room(String roomName, String roomDescription, BigDecimal roomBasePrice, Number floorNumber, Number roomNumber, String roomStatus) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomBasePrice = roomBasePrice;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.roomStatus = roomStatus;
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private Set<AppPhotos> roomImage = new HashSet<>();
    
    @ManyToMany(mappedBy = "rooms")
    private Set<Reservation> reservations = new HashSet<>();
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "Room_Reservation", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomType_id", referencedColumnName = "id"))
    private Set<RoomType> roomTypes = new HashSet<>();    
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "Room_Utilities", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomUtility_id", referencedColumnName = "id"))
    private Set<Utility> roomUtilities= new HashSet<>();
}
