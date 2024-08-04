package com.example.hotelmanagement.reservation.model;

import com.example.hotelmanagement.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    private long id;
    private Date checkIn;
    private Date checkOut;
    private BigDecimal totalPrice;
    
    @ManyToOne
    @JoinColumn(name = "Owner_Id")
    private User owner;

    public Reservation(Date checkIn, Date checkOut, BigDecimal totalPrice, User owner) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.owner = owner;
    }
}
