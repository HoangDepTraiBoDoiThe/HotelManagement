package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.*;

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
    
    @Column(nullable = false)
    private Date checkIn;
    private Date checkOut;
    
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "Owner_Id")
    private User owner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "Room_Reservation", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private Set<Room> rooms = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "Reservation_Room_Utilities", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomUtility_id", referencedColumnName = "id"))
    private Set<Utility> additionRoomUtility = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
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
