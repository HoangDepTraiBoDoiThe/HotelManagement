package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Utility extends BaseModel {
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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "additionRoomUtility", fetch = FetchType.LAZY)
    private Set<RoomReservation> roomReservations = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "roomUtilities", fetch = FetchType.LAZY)
    private Set<Room> rooms = new HashSet<>();
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "roomTypeUtilities", fetch = FetchType.LAZY)
    private Set<RoomType> roomTypes = new HashSet<>();
}
