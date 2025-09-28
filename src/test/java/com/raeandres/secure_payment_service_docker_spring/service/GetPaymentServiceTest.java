package com.raeandres.secure_payment_service_docker_spring.service;

import com.raeandres.secure_payment_service_docker_spring.domain.PaymentRepository;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    private GetPaymentService getPaymentService;

    @BeforeEach
    void setUp() {
        getPaymentService = new GetPaymentService(paymentRepository);
    }

    @Test
    void execute_WithPayments() {
        PaymentEntity payment1 = new PaymentEntity();
        payment1.setTransactionId("txn_1");
        payment1.setAmount(100.0);
        payment1.setStatus("SUBMITTED");
        payment1.setCreatedAt(new Date());

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setTransactionId("txn_2");
        payment2.setAmount(200.0);
        payment2.setStatus("CONFIRMED");
        payment2.setCreatedAt(new Date());

        List<PaymentEntity> payments = Arrays.asList(payment1, payment2);
        when(paymentRepository.findAll()).thenReturn(payments);

        ResponseEntity<ArrayList<String>> response = getPaymentService.execute(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void execute_WithNoPayments() {
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<ArrayList<String>> response = getPaymentService.execute(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}
