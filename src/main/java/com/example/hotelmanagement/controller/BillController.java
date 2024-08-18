package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.dto.request.BillRequest;
import com.example.hotelmanagement.dto.response.BillResponse;
import com.example.hotelmanagement.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bill")
public class BillController {
    private final BillService billService;

    @GetMapping("/{bill_id}")
    public ResponseEntity<?> getBillById(@PathVariable long bill_id, Authentication authentication) {
        EntityModel<BillResponse> responseEntityModel = billService.getBillById(bill_id, authentication);
        return ResponseEntity.ok(responseEntityModel);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createBill(@RequestBody @Validated BillRequest billRequest, Authentication authentication) {
        EntityModel<BillResponse> billRequestEntityModel = billService.createBill(billRequest, authentication);
        return ResponseEntity.ok(billRequestEntityModel);
    }

    @PutMapping("/{bill_id}")
    public ResponseEntity<?> updateBill(@PathVariable long bill_id, @RequestBody @Validated BillRequest billRequest, Authentication authentication) {
        EntityModel<BillResponse> billRequestEntityModel = billService.updateBill(bill_id, billRequest, authentication);
        return ResponseEntity.ok(billRequestEntityModel);
    }

    @DeleteMapping("/delete/{bill_id}")
    public ResponseEntity<?> deleteBill(@PathVariable long bill_id) {
        billService.deleteBill(bill_id);
        return ResponseEntity.noContent().build();
    }
}
