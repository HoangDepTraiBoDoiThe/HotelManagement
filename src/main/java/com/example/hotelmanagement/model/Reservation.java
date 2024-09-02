package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseModel {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "Owner_Id")
    private User owner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(orphanRemoval = true, mappedBy = "reservation", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<RoomReservation> roomReservation = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "reservation")
    private Bill reservationBill;
    
    public Reservation(User owner, Set<RoomReservation> roomReservation) {
        this.owner = owner;
        this.roomReservation = roomReservation;
    }    
    public Reservation(User owner) {
        this.owner = owner;
    }
}
