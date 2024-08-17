package com.example.hotelmanagement.model;

import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.service.RoomService;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Data
@Entity(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private long id;
    
    @NotBlank
    @PastOrPresent
    @Column(nullable = false)
    private Date checkIn;
    private Date checkOut;
    
    @NotBlank
    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "Owner_Id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "Room_Reservation", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Reservation_Room_Utilities", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomUtility_id", referencedColumnName = "id"))
    private Set<Utility> additionRoomUtility = new HashSet<>();
    
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "reservation")
    private Bill reservationBill;
    
    public Reservation(Date checkIn, Date checkOut, BigDecimal totalPrice, User owner, Set<Room> rooms, Set<Utility> additionRoomUtility) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.owner = owner;
        this.rooms = rooms;
        this.additionRoomUtility = additionRoomUtility;
    }
}
