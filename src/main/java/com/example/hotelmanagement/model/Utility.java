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
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Utility {
    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(mappedBy = "additionRoomUtility")
    private List<Reservation> reservations = new ArrayList<>();

    @NotBlank
    @Column(nullable = false)
    @Size(max = 20, message = "20 characters limited. Name should be short and directive, any more information can be put in the description.")
    private String utilityName;

    @NotBlank
    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal utilityBasePrice;

    @Size(max = 2000)
    private String utilityDescription;

    @Column(nullable = false)
    private boolean utilityStatus;

    @ManyToMany(mappedBy = "roomUtilities")
    private List<Room> rooms = new ArrayList<>();

    @ManyToMany(mappedBy = "roomTypeUtilities")
    private List<RoomType> roomTypes = new ArrayList<>();
}
