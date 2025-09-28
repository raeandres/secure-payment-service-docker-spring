package com.raeandres.secure_payment_service_docker_spring.controller;

import com.raeandres.secure_payment_service_docker_spring.service.ConfirmPaymentService;
import com.raeandres.secure_payment_service_docker_spring.service.GetPaymentService;
import com.raeandres.secure_payment_service_docker_spring.service.SubmitPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private SubmitPaymentService submitPaymentService;

    @Autowired
    private ConfirmPaymentService confirmPaymentService;

    @Autowired
    private GetPaymentService getPaymentService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitPayment() {
        return submitPaymentService.execute();
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment() {
        return confirmPaymentService.execute();
    }

    @GetMapping("/payments")
    public ResponseEntity<ArrayList<String>> getPayments() {
        return getPaymentService.execute();
    }

//    @PutMapping
//
//    @DeleteMapping

}
