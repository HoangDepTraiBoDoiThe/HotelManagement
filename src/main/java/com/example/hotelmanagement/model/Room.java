package com.example.hotelmanagement.model;

import com.example.hotelmanagement.helper.RoomStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(max = 20, message = "20 characters limited. Room name should be short and directive, any more information can be put in the description.")
    private String roomName;
    
    @Column(nullable = false)
    private Number floorNumber;
    
    @Column(nullable = false)
    @NotBlank
    @Positive
    private String roomNumber;

    @Column(nullable = false)
    private RoomStatus roomStatus = RoomStatus.ROOM_OUT_OF_ORDER;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private List<AppPhotos> roomImage = new ArrayList<>();
    
    @ManyToMany(mappedBy = "rooms")
    private List<Reservation> reservations = new ArrayList<>();
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "Room_Reservation", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomType_id", referencedColumnName = "id"))
    private List<RoomType> roomTypes = new ArrayList<>();    
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "Room_Utilities", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomUtility_id", referencedColumnName = "id"))
    private List<Utility> roomUtilities= new ArrayList<>();
}
