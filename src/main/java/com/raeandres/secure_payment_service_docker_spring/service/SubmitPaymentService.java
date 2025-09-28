package com.raeandres.secure_payment_service_docker_spring.service;

import com.raeandres.secure_payment_service_docker_spring.service.cqrs.Command;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubmitPaymentService implements Command<Void, String> {

    @Override
    public ResponseEntity<String> execute(Void input) {
        return ResponseEntity.status(HttpStatus.OK).body("Payment Submitted!");
    }
}
