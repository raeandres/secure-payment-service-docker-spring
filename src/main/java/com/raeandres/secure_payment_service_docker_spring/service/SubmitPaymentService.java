package com.raeandres.secure_payment_service_docker_spring.service;

import com.raeandres.secure_payment_service_docker_spring.domain.PaymentRepository;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentEntity;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentEvent;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentRequest;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentStatus;
import com.raeandres.secure_payment_service_docker_spring.service.cqrs.Command;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SubmitPaymentService implements Command<PaymentRequest, String> {

    private final PaymentRepository mRepository;
    private final OutboxService outboxService;
    
    public SubmitPaymentService(PaymentRepository repository, OutboxService outboxService) {
       this.mRepository = repository;
       this.outboxService = outboxService;
    }

    @Override
    @Transactional
    public ResponseEntity<String> execute(PaymentRequest request) {
        if (request.getTransactionId().isEmpty() || request.getReferenceId().isEmpty()) {
            throw new IllegalArgumentException("transaction ID and reference ID are missing");
        }

        PaymentEntity entity = new PaymentEntity();
        entity.setTransactionId(request.getTransactionId());
        entity.setAccountId(request.getAccountFrom());
        entity.setAmount(Double.parseDouble(request.getAmount()));
        entity.setStatus(PaymentStatus.SUBMITTED.name());
        entity.setCreatedAt(new Date(System.currentTimeMillis()));
        entity.setModifiedAt(new Date(System.currentTimeMillis()));
        
        mRepository.save(entity);
        
        PaymentEvent event = new PaymentEvent(
            entity.getTransactionId(),
            entity.getStatus(),
            entity.getAmount(),
            entity.getAccountId()
        );
        
        outboxService.saveEvent("PAYMENT_SUBMITTED", event);
        
        return ResponseEntity.status(HttpStatus.OK).body("Payment Submitted!");
    }
}
