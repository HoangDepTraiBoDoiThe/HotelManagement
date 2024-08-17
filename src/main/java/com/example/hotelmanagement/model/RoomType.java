package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 50,  nullable = false)
    private String typeName;
    private String description;

    @Column(nullable = false)
    private Number capacity;
    @Column(nullable = false)
    private BigDecimal basePrice;

    public RoomType(String typeName, String description, Number capacity, BigDecimal basePrice) {
        this.typeName = typeName;
        this.description = description;
        this.capacity = capacity;
        this.basePrice = basePrice;
    }

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "roomTypes", fetch = FetchType.LAZY)
    private Set<Room> rooms = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Room_Type_Utilities", joinColumns = @JoinColumn(referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private Set<Utility> roomTypeUtilities = new HashSet<>();
}
