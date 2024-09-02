package com.example.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bill extends BaseModel {
    @Column(nullable = false)
    private LocalDate date;
    
    @OneToOne(orphanRemoval = true)
    @JoinColumn(referencedColumnName = "id")
    private Reservation reservation;

    public Bill(LocalDate date) {
        this.date = date;
    }
}
