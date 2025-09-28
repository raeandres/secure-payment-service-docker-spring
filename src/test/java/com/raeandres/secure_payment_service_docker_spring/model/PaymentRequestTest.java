package com.raeandres.secure_payment_service_docker_spring.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRequestTest {

    @Test
    void testPaymentRequestCreation() {
        PaymentRequest request = new PaymentRequest();
        
        assertNotNull(request);
    }

    @Test
    void testPaymentRequestSettersAndGetters() {
        PaymentRequest request = new PaymentRequest();
        
        request.setTransactionId("txn_123");
        request.setAmount("100.50");
        request.setReferenceId("ref_456");
        request.setAccountFrom("acc_from_789");
        request.setAccountTo("acc_to_101");
        
        assertEquals("txn_123", request.getTransactionId());
        assertEquals("100.50", request.getAmount());
        assertEquals("ref_456", request.getReferenceId());
        assertEquals("acc_from_789", request.getAccountFrom());
        assertEquals("acc_to_101", request.getAccountTo());
    }

    @Test
    void testPaymentRequestWithNullValues() {
        PaymentRequest request = new PaymentRequest();
        
        request.setTransactionId(null);
        request.setAmount(null);
        
        assertNull(request.getTransactionId());
        assertNull(request.getAmount());
    }
}
