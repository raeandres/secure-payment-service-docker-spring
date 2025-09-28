package com.raeandres.secure_payment_service_docker_spring.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/submit")
    public String submitPayment() {
        return "Payment Submitted!";
    }

    @PostMapping("/confirm")
    public String confirmPayment() {
        return "Payment Confirmed!";
    }

    @GetMapping("/payments")
    public ArrayList<String> getPayments() {
        ArrayList<String> payments = new ArrayList<>();
        for (int i = 0; i < 100; i ++) {
            payments.add(i + ":: New Payment");
        }
        return payments;
    }

//    @PutMapping
//
//    @DeleteMapping

}
