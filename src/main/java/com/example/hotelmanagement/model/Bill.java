package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private LocalDate date;
    
    @OneToOne(orphanRemoval = true)
    @JoinColumn(referencedColumnName = "id")
    private Reservation reservation;
}
