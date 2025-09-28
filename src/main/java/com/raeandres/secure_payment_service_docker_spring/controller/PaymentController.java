package com.raeandres.secure_payment_service_docker_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/submit")
    public ResponseEntity<String> submitPayment() {
        return ResponseEntity.status(HttpStatus.OK).body("Payment Submitted!");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment() {
        return ResponseEntity.status(HttpStatus.CREATED).body("Payment Confirmed!");
    }

    @GetMapping("/payments")
    public ResponseEntity<ArrayList<String>> getPayments() {
        ArrayList<String> payments = new ArrayList<>();
        for (int i = 0; i < 100; i ++) {
            payments.add(i + ":: New Payment");
        }
        return ResponseEntity.status(HttpStatus.OK).body(payments);
    }

//    @PutMapping
//
//    @DeleteMapping

}
