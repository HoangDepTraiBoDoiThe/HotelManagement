package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne(mappedBy = "reservationBill", orphanRemoval = true)
    @JoinColumn(referencedColumnName = "id")
    private Reservation reservation;
}
