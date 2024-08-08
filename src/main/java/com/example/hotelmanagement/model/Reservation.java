package com.example.hotelmanagement.model;

import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.service.RoomService;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private long id;
    
    @NotBlank
    @PastOrPresent
    @Column(nullable = false)
    private Date checkIn;
    private Date checkOut;
    
    @NotBlank
    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "Owner_Id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "Room_Reservation", joinColumns = @JoinColumn(name = "reservatino_is", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private List<Room> rooms = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "Reservation_Room_Utilities", joinColumns = @JoinColumn(name = "reservatino_is", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roomUtility_id", referencedColumnName = "id"))
    private List<Utility> additionRoomUtility = new ArrayList<>();
    
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "reservation")
    private Bill reservationBill;
    
    public Reservation(Date checkIn, Date checkOut, BigDecimal totalPrice, User owner) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.owner = owner;
    }
}
