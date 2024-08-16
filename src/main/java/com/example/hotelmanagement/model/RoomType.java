package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class RoomType {
    @Id
    @GeneratedValue
    private long Id;

    @NotBlank
    @Size(max = 20, message = "20 characters limited. Room type name should be short and directive, any more information can be put in the description.")
    private String typeName;
    private String description;
    
    @NotBlank
    @Positive
    @Min(1)
    private Number capacity;
    
    @NotBlank
    @PositiveOrZero
    private BigDecimal basePrice;

    public RoomType(String typeName, String description, Number capacity, BigDecimal basePrice) {
        this.typeName = typeName;
        this.description = description;
        this.capacity = capacity;
        this.basePrice = basePrice;
    }

    @ManyToMany(mappedBy = "roomTypes")
    private Set<Room> rooms = new HashSet<>();
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "Room_Type_Utilities", joinColumns = @JoinColumn(referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private Set<Utility> roomTypeUtilities = new HashSet<>();
    
    
}
