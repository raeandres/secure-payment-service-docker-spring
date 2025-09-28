package com.raeandres.secure_payment_service_docker_spring.service;

import com.raeandres.secure_payment_service_docker_spring.domain.OutboxEventRepository;
import com.raeandres.secure_payment_service_docker_spring.messaging.KafkaProducerService;
import com.raeandres.secure_payment_service_docker_spring.model.OutboxEvent;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutboxServiceTest {

    @Mock
    private OutboxEventRepository outboxEventRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    private OutboxService outboxService;

    @BeforeEach
    void setUp() {
        outboxService = new OutboxService(outboxEventRepository, kafkaProducerService);
    }

    @Test
    void saveEvent_Success() {
        PaymentEvent paymentEvent = new PaymentEvent("txn_123", "SUBMITTED", 100.0, "acc_456");

        outboxService.saveEvent("PAYMENT_SUBMITTED", paymentEvent);

        verify(outboxEventRepository).save(any(OutboxEvent.class));
    }

    @Test
    void processUnprocessedEvents_Success() {
        OutboxEvent event1 = new OutboxEvent();
        event1.setEventType("PAYMENT_SUBMITTED");
        event1.setPayload("{\"transactionId\":\"txn_123\"}");
        event1.setProcessed(false);

        OutboxEvent event2 = new OutboxEvent();
        event2.setEventType("PAYMENT_CONFIRMED");
        event2.setPayload("{\"transactionId\":\"txn_456\"}");
        event2.setProcessed(false);

        List<OutboxEvent> unprocessedEvents = Arrays.asList(event1, event2);
        when(outboxEventRepository.findByProcessedFalse()).thenReturn(unprocessedEvents);

        outboxService.processUnprocessedEvents();

        verify(kafkaProducerService, times(2)).sendMessage(anyString());
        verify(outboxEventRepository, times(2)).save(any(OutboxEvent.class));
    }

    @Test
    void processUnprocessedEvents_NoEvents() {
        when(outboxEventRepository.findByProcessedFalse()).thenReturn(Arrays.asList());

        outboxService.processUnprocessedEvents();

        verify(kafkaProducerService, never()).sendMessage(anyString());
        verify(outboxEventRepository, never()).save(any(OutboxEvent.class));
    }

    @Test
    void processUnprocessedEvents_KafkaException() {
        OutboxEvent event = new OutboxEvent();
        event.setEventType("PAYMENT_SUBMITTED");
        event.setPayload("{\"transactionId\":\"txn_123\"}");
        event.setProcessed(false);

        when(outboxEventRepository.findByProcessedFalse()).thenReturn(Arrays.asList(event));
        doThrow(new RuntimeException("Kafka error")).when(kafkaProducerService).sendMessage(anyString());

        outboxService.processUnprocessedEvents();

        verify(kafkaProducerService).sendMessage(anyString());
        verify(outboxEventRepository, never()).save(any(OutboxEvent.class));
    }
}
