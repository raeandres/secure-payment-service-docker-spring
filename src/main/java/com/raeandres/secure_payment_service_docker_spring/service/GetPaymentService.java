package com.raeandres.secure_payment_service_docker_spring.service;

import com.raeandres.secure_payment_service_docker_spring.model.PaymentEntity;
import com.raeandres.secure_payment_service_docker_spring.service.cqrs.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetPaymentService implements Query<Void, ArrayList<String>> {

    @Override
    public ResponseEntity<ArrayList<String>> execute(Void input) {
        ArrayList<String> payments = new ArrayList<>();
        for (int i = 0; i < 100; i ++) {
            payments.add(i + ":: New Payment");
        }
        return ResponseEntity.status(HttpStatus.OK).body(payments);
    }
}
