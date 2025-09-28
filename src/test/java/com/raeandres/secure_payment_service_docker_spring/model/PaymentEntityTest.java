package com.raeandres.secure_payment_service_docker_spring.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PaymentEntityTest {

    @Test
    void testPaymentEntityCreation() {
        PaymentEntity entity = new PaymentEntity();
        
        assertNotNull(entity);
        assertNull(entity.getTransactionId());
        assertNull(entity.getAmount());
        assertNull(entity.getStatus());
    }

    @Test
    void testPaymentEntitySettersAndGetters() {
        PaymentEntity entity = new PaymentEntity();
        Date now = new Date();
        
        entity.setTransactionId("txn_123");
        entity.setAmount(100.50);
        entity.setStatus("PROCESSING");
        entity.setSessionId("session_456");
        entity.setAccountId("acc_789");
        entity.setCreatedAt(now);
        entity.setModifiedAt(now);
        
        assertEquals("txn_123", entity.getTransactionId());
        assertEquals(100.50, entity.getAmount());
        assertEquals("PROCESSING", entity.getStatus());
        assertEquals("session_456", entity.getSessionId());
        assertEquals("acc_789", entity.getAccountId());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getModifiedAt());
    }

    @Test
    void testPaymentEntityWithNullValues() {
        PaymentEntity entity = new PaymentEntity();
        
        entity.setTransactionId(null);
        entity.setAmount(null);
        entity.setStatus(null);
        
        assertNull(entity.getTransactionId());
        assertNull(entity.getAmount());
        assertNull(entity.getStatus());
    }
}
