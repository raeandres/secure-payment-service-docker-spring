package com.raeandres.secure_payment_service_docker_spring.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConfirmPaymentService {

    public ResponseEntity<String> execute(){
        return ResponseEntity.status(HttpStatus.CREATED).body("Payment Confirmed!");
    }
}
