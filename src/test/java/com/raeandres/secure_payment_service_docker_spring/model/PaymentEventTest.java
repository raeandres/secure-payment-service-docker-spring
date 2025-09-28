package com.raeandres.secure_payment_service_docker_spring.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentEventTest {

    @Test
    void testPaymentEventCreation() {
        PaymentEvent event = new PaymentEvent("txn_123", "SUBMITTED", 100.0, "acc_456");
        
        assertNotNull(event);
        assertEquals("txn_123", event.getTransactionId());
        assertEquals("SUBMITTED", event.getStatus());
        assertEquals(100.0, event.getAmount());
        assertEquals("acc_456", event.getAccountId());
    }

    @Test
    void testPaymentEventSettersAndGetters() {
        PaymentEvent event = new PaymentEvent();
        
        event.setTransactionId("txn_789");
        event.setStatus("CONFIRMED");
        event.setAmount(250.75);
        event.setAccountId("acc_101");
        
        assertEquals("txn_789", event.getTransactionId());
        assertEquals("CONFIRMED", event.getStatus());
        assertEquals(250.75, event.getAmount());
        assertEquals("acc_101", event.getAccountId());
    }

    @Test
    void testPaymentEventWithNullValues() {
        PaymentEvent event = new PaymentEvent();
        
        event.setTransactionId(null);
        event.setStatus(null);
        
        assertNull(event.getTransactionId());
        assertNull(event.getStatus());
    }
}
