package com.raeandres.secure_payment_service_docker_spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raeandres.secure_payment_service_docker_spring.domain.OutboxEventRepository;
import com.raeandres.secure_payment_service_docker_spring.messaging.KafkaProducerService;
import com.raeandres.secure_payment_service_docker_spring.model.OutboxEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboxService {
    private final OutboxEventRepository outboxRepository;
    private final KafkaProducerService kafkaProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OutboxService(OutboxEventRepository outboxRepository, KafkaProducerService kafkaProducer) {
        this.outboxRepository = outboxRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Transactional
    public void saveEvent(String eventType, Object payload) {
        try {
            OutboxEvent event = new OutboxEvent();
            event.setEventType(eventType);
            event.setPayload(objectMapper.writeValueAsString(payload));
            outboxRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save outbox event", e);
        }
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishPendingEvents() {
        outboxRepository.findByProcessedFalse().forEach(event -> {
            kafkaProducer.sendMessage(event.getPayload());
            event.setProcessed(true);
            outboxRepository.save(event);
        });
    }
}
