package com.raeandres.secure_payment_service_docker_spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IdempotencyServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    private IdempotencyService idempotencyService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        idempotencyService = new IdempotencyService(redisTemplate);
    }

    @Test
    void isProcessed_ReturnsTrueWhenExists() {
        when(valueOperations.get("idempotency:txn_123")).thenReturn("processed");

        boolean result = idempotencyService.isProcessed("txn_123");

        assertTrue(result);
        verify(valueOperations).get("idempotency:txn_123");
    }

    @Test
    void isProcessed_ReturnsFalseWhenNotExists() {
        when(valueOperations.get("idempotency:txn_456")).thenReturn(null);

        boolean result = idempotencyService.isProcessed("txn_456");

        assertFalse(result);
        verify(valueOperations).get("idempotency:txn_456");
    }

    @Test
    void markAsProcessed_Success() {
        idempotencyService.markAsProcessed("txn_789");

        verify(valueOperations).set("idempotency:txn_789", "processed");
    }

    @Test
    void isProcessed_WithNullTransactionId() {
        boolean result = idempotencyService.isProcessed(null);

        assertFalse(result);
        verify(valueOperations, never()).get(anyString());
    }

    @Test
    void markAsProcessed_WithNullTransactionId() {
        idempotencyService.markAsProcessed(null);

        verify(valueOperations, never()).set(anyString(), anyString());
    }
}
