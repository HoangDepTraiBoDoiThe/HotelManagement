package com.example.hotelmanagement.service;

import com.example.hotelmanagement.controller.assembler.BillAssembler;
import com.example.hotelmanagement.controller.assembler.ReservationAssembler;
import com.example.hotelmanagement.dto.request.BillRequest;
import com.example.hotelmanagement.dto.response.BillResponse;
import com.example.hotelmanagement.dto.response.ReservationResponse;
import com.example.hotelmanagement.exception.ResourceNotFoundException;
import com.example.hotelmanagement.model.Bill;
import com.example.hotelmanagement.model.Reservation;
import com.example.hotelmanagement.model.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final ReservationService reservationService;
    private final ReservationAssembler reservationAssembler;
    private final BillAssembler billAssembler;
    
    public EntityModel<BillResponse> getBillById(long id, Authentication authentication) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Bill with id %d not found", id)));
        return billAssembler.toModel(makeBillResponse(bill, authentication), authentication);
    }
    
    public EntityModel<BillResponse> createBill(long reservation_id, BillRequest billRequest, Authentication authentication) {
        Bill newBill = billRequest.toModel();
        Reservation reservation = reservationService.getReservation_entity(reservation_id);
        newBill.setReservation(reservation);
        
        Bill newCreatedBill = billRepository.save(newBill);
        
        return billAssembler.toModel(makeBillResponse(newCreatedBill, authentication), authentication);
    }
    
    public EntityModel<BillResponse> updateBill(long bill_id, BillRequest billRequest, Authentication authentication) {
        Bill bill = billRepository.findById(bill_id).orElseThrow(() -> new ResourceNotFoundException(String.format("Bill with id %d not found", id)));
        bill.setDate(billRequest.getDate());
        
        Bill newCreatedBill = billRepository.save(bill);
        return billAssembler.toModel(makeBillResponse(newCreatedBill, authentication), authentication);
    }

    public void deleteBill(long id) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Bill with id %d not found", id)));
        billRepository.delete(bill);
    }

    public BillResponse makeBillResponse(Bill bill, Authentication authentication) {
        EntityModel<ReservationResponse> responseEntityModel = reservationAssembler.toModel(reservationService.makeReservationResponse(bill.getReservation(), authentication), authentication);
        return new BillResponse(bill, reservationAssembler.toModel(new BillResponse(bill, responseEntityModel), authentication));
    }
}
