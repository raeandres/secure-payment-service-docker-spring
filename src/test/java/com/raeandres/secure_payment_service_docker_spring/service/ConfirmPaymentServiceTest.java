package com.raeandres.secure_payment_service_docker_spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmPaymentServiceTest {

    private ConfirmPaymentService confirmPaymentService;

    @BeforeEach
    void setUp() {
        confirmPaymentService = new ConfirmPaymentService();
    }

    @Test
    void execute_Success() {
        ResponseEntity<String> response = confirmPaymentService.execute(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment Confirmed!", response.getBody());
    }

    @Test
    void execute_WithAnyInput() {
        ResponseEntity<String> response = confirmPaymentService.execute("any input");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment Confirmed!", response.getBody());
    }
}
