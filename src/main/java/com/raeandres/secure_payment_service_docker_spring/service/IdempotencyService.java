package com.raeandres.secure_payment_service_docker_spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class IdempotencyService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String IDEMPOTENCY_PREFIX = "payment:idempotent:";
    private static final Duration TTL = Duration.ofHours(24);

    public boolean isProcessed(String transactionId) {
        return redisTemplate.hasKey(IDEMPOTENCY_PREFIX + transactionId);
    }

    public void markAsProcessed(String transactionId) {
        redisTemplate.opsForValue().set(IDEMPOTENCY_PREFIX + transactionId, "processed", TTL);
    }
}
