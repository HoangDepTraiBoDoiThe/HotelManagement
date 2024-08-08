package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {
    @Id
    @GeneratedValue
    private long Id;

    private String description;
    private String typeName;
    private Number capacity;
    private BigDecimal basePrice;

    @ManyToMany(mappedBy = "roomTypes")
    private List<Room> rooms = new ArrayList<>();
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "room_type_image", joinColumns = @JoinColumn(name = "roomType_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private List<AppPhotos> roomTypeImages;
}
