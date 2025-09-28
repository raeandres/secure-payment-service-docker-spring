package com.raeandres.secure_payment_service_docker_spring.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OutboxEventTest {

    @Test
    void testOutboxEventCreation() {
        OutboxEvent event = new OutboxEvent();
        
        assertNotNull(event);
        assertNull(event.getEventType());
        assertNull(event.getPayload());
        assertFalse(event.isProcessed());
    }

    @Test
    void testOutboxEventSettersAndGetters() {
        OutboxEvent event = new OutboxEvent();
        Date now = new Date();
        
        event.setEventType("PAYMENT_SUBMITTED");
        event.setPayload("{\"transactionId\":\"txn_123\"}");
        event.setProcessed(true);
        event.setCreatedAt(now);
        
        assertEquals("PAYMENT_SUBMITTED", event.getEventType());
        assertEquals("{\"transactionId\":\"txn_123\"}", event.getPayload());
        assertTrue(event.isProcessed());
        assertEquals(now, event.getCreatedAt());
    }

    @Test
    void testOutboxEventDefaultProcessedState() {
        OutboxEvent event = new OutboxEvent();
        
        assertFalse(event.isProcessed());
        
        event.setProcessed(true);
        assertTrue(event.isProcessed());
    }
}
