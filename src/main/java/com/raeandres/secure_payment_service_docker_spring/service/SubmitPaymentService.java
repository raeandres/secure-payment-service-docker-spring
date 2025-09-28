package com.raeandres.secure_payment_service_docker_spring.service;

import com.raeandres.secure_payment_service_docker_spring.domain.PaymentRepository;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentEntity;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentRequest;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentStatus;
import com.raeandres.secure_payment_service_docker_spring.service.cqrs.Command;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SubmitPaymentService implements Command<PaymentRequest, String> {

    private final PaymentRepository mRepository;
    public SubmitPaymentService(PaymentRepository repository) {
       this.mRepository = repository;
    }

    @Override
    public ResponseEntity<String> execute(PaymentRequest request) {

        PaymentEntity entity = new PaymentEntity();
        entity.setTransactionId(request.getTransactionId());
        entity.setAccountId(request.getAccountFrom());
        entity.setAmount(Double.parseDouble(request.getAmount()));

        entity.setTransactionId(request.getTransactionId());
        entity.setCreatedAt(new Date(System.currentTimeMillis()));
        entity.setModifiedAt(new Date(System.currentTimeMillis()));
        mRepository.save(entity);
        if (request.getTransactionId().isEmpty() || request.getReferenceId().isEmpty()) {
            entity.setStatus(PaymentStatus.REJECTED.name());
            throw new IllegalArgumentException("transaction ID and reference ID are missing");
        } else {
            entity.setStatus(PaymentStatus.SUBMITTED.name());
            return ResponseEntity.status(HttpStatus.OK).body("Payment Submitted!");
        }
    }
}
