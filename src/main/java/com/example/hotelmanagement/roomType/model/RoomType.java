package com.example.hotelmanagement.roomType.model;

import com.example.hotelmanagement.room.model.Room;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private List<Room> rooms;
}
