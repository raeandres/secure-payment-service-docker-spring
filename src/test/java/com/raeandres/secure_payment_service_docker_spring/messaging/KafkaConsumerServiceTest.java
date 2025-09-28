package com.raeandres.secure_payment_service_docker_spring.messaging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    private KafkaConsumerService kafkaConsumerService;

    @BeforeEach
    void setUp() {
        kafkaConsumerService = new KafkaConsumerService();
    }

    @Test
    void listen_Success() {
        String message = "Test payment event message";

        assertDoesNotThrow(() -> {
            kafkaConsumerService.listen(message);
        });
    }

    @Test
    void listen_WithNullMessage() {
        assertDoesNotThrow(() -> {
            kafkaConsumerService.listen(null);
        });
    }

    @Test
    void listen_WithEmptyMessage() {
        assertDoesNotThrow(() -> {
            kafkaConsumerService.listen("");
        });
    }
}
