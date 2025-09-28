package com.raeandres.secure_payment_service_docker_spring.controller;

import com.raeandres.secure_payment_service_docker_spring.model.PaymentRequest;
import com.raeandres.secure_payment_service_docker_spring.service.ConfirmPaymentService;
import com.raeandres.secure_payment_service_docker_spring.service.GetPaymentService;
import com.raeandres.secure_payment_service_docker_spring.service.SubmitPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final SubmitPaymentService submitPaymentService;
    private final ConfirmPaymentService confirmPaymentService;
    private final GetPaymentService getPaymentService;

    public PaymentController(SubmitPaymentService submitPaymentService,
                             ConfirmPaymentService confirmPaymentService,
                             GetPaymentService getPaymentService) {
        this.submitPaymentService = submitPaymentService;
        this.confirmPaymentService = confirmPaymentService;
        this.getPaymentService = getPaymentService;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitPayment(@RequestBody PaymentRequest request) {
        return submitPaymentService.execute(request);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment() {
        return confirmPaymentService.execute(null);
    }

    @GetMapping("/payments")
    public ResponseEntity<ArrayList<String>> getPayments() {
        return getPaymentService.execute(null);
    }

//    @PutMapping
//
//    @DeleteMapping

}
