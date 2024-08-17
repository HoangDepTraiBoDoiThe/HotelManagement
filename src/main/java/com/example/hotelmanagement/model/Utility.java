package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Utility {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 100, nullable = false)
    private String utilityName;

    @Column(nullable = false)
    private BigDecimal utilityBasePrice;

    @Column(length = 2000)
    private String utilityDescription;

    @Column(nullable = false)
    private boolean utilityStatus;

    public Utility(String utilityName, BigDecimal utilityBasePrice, String utilityDescription, boolean utilityStatus) {
        this.utilityName = utilityName;
        this.utilityBasePrice = utilityBasePrice;
        this.utilityDescription = utilityDescription;
        this.utilityStatus = utilityStatus;
    }

    @ManyToMany(mappedBy = "additionRoomUtility")
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToMany(mappedBy = "roomUtilities")
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany(mappedBy = "roomTypeUtilities")
    private Set<RoomType> roomTypes = new HashSet<>();
}
