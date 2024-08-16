package com.example.hotelmanagement.dto.response;

import com.example.hotelmanagement.model.Bill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse extends ResponseBase {
    private LocalDate date;
    private EntityModel<?> reservation;

    public BillResponse(Bill bill, EntityModel<?> reservation) {
        super(bill.getId());
        this.date = bill.getDate();
        this.reservation = reservation;
    }
}
