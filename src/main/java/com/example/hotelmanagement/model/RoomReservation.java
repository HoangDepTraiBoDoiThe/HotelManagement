package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_checkout", columnList = "checkout")
})
public class RoomReservation extends BaseModel {
    private LocalDate checkin;
    private LocalDate checkout;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    public RoomReservation(LocalDate checkin, LocalDate checkout, Room room, Reservation reservation, Set<Utility> additionRoomUtility, BigDecimal totalPrice) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.room = room;
        this.reservation = reservation;
        this.totalPrice = totalPrice;
        this.additionRoomUtility = additionRoomUtility;
    }

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "Room_Reservation_Utilities", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomUtility_id", referencedColumnName = "id"))
    private Set<Utility> additionRoomUtility = new HashSet<>();
}
