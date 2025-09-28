package com.raeandres.secure_payment_service_docker_spring.service;

import com.raeandres.secure_payment_service_docker_spring.domain.PaymentRepository;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentEntity;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitPaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OutboxService outboxService;

    @Mock
    private IdempotencyService idempotencyService;

    private SubmitPaymentService submitPaymentService;

    @BeforeEach
    void setUp() {
        submitPaymentService = new SubmitPaymentService(paymentRepository, outboxService, idempotencyService);
    }

    @Test
    void execute_Success() {
        PaymentRequest request = new PaymentRequest();
        request.setTransactionId("txn_123");
        request.setReferenceId("ref_456");
        request.setAmount("100.00");
        request.setAccountFrom("acc_from");

        when(idempotencyService.isProcessed(anyString())).thenReturn(false);
        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(new PaymentEntity());

        ResponseEntity<String> response = submitPaymentService.execute(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment Submitted!", response.getBody());
        verify(paymentRepository).save(any(PaymentEntity.class));
        verify(idempotencyService).markAsProcessed("txn_123");
        verify(outboxService).saveEvent(eq("PAYMENT_SUBMITTED"), any());
    }

    @Test
    void execute_IdempotentRequest() {
        PaymentRequest request = new PaymentRequest();
        request.setTransactionId("txn_123");
        request.setReferenceId("ref_456");

        when(idempotencyService.isProcessed("txn_123")).thenReturn(true);

        ResponseEntity<String> response = submitPaymentService.execute(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment already processed (idempotent)", response.getBody());
        verify(paymentRepository, never()).save(any());
        verify(outboxService, never()).saveEvent(anyString(), any());
    }

    @Test
    void execute_MissingTransactionId() {
        PaymentRequest request = new PaymentRequest();
        request.setTransactionId("");
        request.setReferenceId("ref_456");

        assertThrows(IllegalArgumentException.class, () -> {
            submitPaymentService.execute(request);
        });
    }

    @Test
    void execute_MissingReferenceId() {
        PaymentRequest request = new PaymentRequest();
        request.setTransactionId("txn_123");
        request.setReferenceId("");

        assertThrows(IllegalArgumentException.class, () -> {
            submitPaymentService.execute(request);
        });
    }
}
