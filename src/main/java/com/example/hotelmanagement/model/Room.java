package com.example.hotelmanagement.model;

import com.example.hotelmanagement.constants.RoomStatus;
import jakarta.persistence.*;
import lombok.*;

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
    private Integer floorNumber;
    
    @Column(nullable = false)
    private Integer roomNumber;

    @Column(nullable = false)
    private String roomStatus = RoomStatus.ROOM_OUT_OF_ORDER.toString();

    public Room(String roomName, String roomDescription, BigDecimal roomBasePrice, Integer floorNumber, Integer roomNumber, String roomStatus) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomBasePrice = roomBasePrice;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.roomStatus = roomStatus;
    }

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(orphanRemoval = true, mappedBy = "room")
    private Set<RoomReservation> roomReservations = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private Set<AppPhotos> roomImage = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "Room_RoomType", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomType_id", referencedColumnName = "id"))
    private Set<RoomType> roomTypes = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "Room_Utilities", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomUtility_id", referencedColumnName = "id"))
    private Set<Utility> roomUtilities= new HashSet<>();
}
