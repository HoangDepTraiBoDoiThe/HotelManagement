package com.example.hotelmanagement.dto.request;

import com.example.hotelmanagement.model.Bill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    long reservationId;
    LocalDate date;
    
    public Bill toModel() {
        return new Bill(date);
    }
}
