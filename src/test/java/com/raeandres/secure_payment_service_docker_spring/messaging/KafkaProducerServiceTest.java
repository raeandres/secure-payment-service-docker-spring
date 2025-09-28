package com.raeandres.secure_payment_service_docker_spring.messaging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        kafkaProducerService = new KafkaProducerService();
        kafkaProducerService.kafkaTemplate = kafkaTemplate;
    }

    @Test
    void sendMessage_Success() {
        String message = "Test payment message";

        kafkaProducerService.sendMessage(message);

        verify(kafkaTemplate).send(KafkaProducerService.TOPIC, message);
    }

    @Test
    void sendMessage_WithNullMessage() {
        kafkaProducerService.sendMessage(null);

        verify(kafkaTemplate).send(KafkaProducerService.TOPIC, null);
    }

    @Test
    void sendMessage_WithEmptyMessage() {
        String message = "";

        kafkaProducerService.sendMessage(message);

        verify(kafkaTemplate).send(KafkaProducerService.TOPIC, message);
    }
}
