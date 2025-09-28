package com.raeandres.secure_payment_service_docker_spring.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStatusTest {

    @Test
    void testPaymentStatusValues() {
        assertEquals("SUBMITTED", PaymentStatus.SUBMITTED.name());
        assertEquals("PROCESSING", PaymentStatus.PROCESSING.name());
        assertEquals("CONFIRMED", PaymentStatus.CONFIRMED.name());
        assertEquals("FAILED", PaymentStatus.FAILED.name());
    }

    @Test
    void testPaymentStatusValueOf() {
        assertEquals(PaymentStatus.SUBMITTED, PaymentStatus.valueOf("SUBMITTED"));
        assertEquals(PaymentStatus.PROCESSING, PaymentStatus.valueOf("PROCESSING"));
        assertEquals(PaymentStatus.CONFIRMED, PaymentStatus.valueOf("CONFIRMED"));
        assertEquals(PaymentStatus.FAILED, PaymentStatus.valueOf("FAILED"));
    }

    @Test
    void testPaymentStatusCount() {
        PaymentStatus[] values = PaymentStatus.values();
        assertEquals(4, values.length);
    }
}
