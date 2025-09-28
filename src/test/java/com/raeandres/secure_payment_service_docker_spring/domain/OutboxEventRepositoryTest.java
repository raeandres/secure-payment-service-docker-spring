package com.raeandres.secure_payment_service_docker_spring.domain;

import com.raeandres.secure_payment_service_docker_spring.model.OutboxEvent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OutboxEventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Test
    void save_Success() {
        OutboxEvent event = new OutboxEvent();
        event.setEventType("PAYMENT_SUBMITTED");
        event.setPayload("{\"transactionId\":\"txn_123\"}");
        event.setProcessed(false);
        event.setCreatedAt(new Date());

        OutboxEvent saved = outboxEventRepository.save(event);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("PAYMENT_SUBMITTED", saved.getEventType());
        assertFalse(saved.isProcessed());
    }

    @Test
    void findByProcessedFalse_Success() {
        OutboxEvent processedEvent = new OutboxEvent();
        processedEvent.setEventType("PAYMENT_CONFIRMED");
        processedEvent.setPayload("{\"transactionId\":\"txn_processed\"}");
        processedEvent.setProcessed(true);
        processedEvent.setCreatedAt(new Date());

        OutboxEvent unprocessedEvent1 = new OutboxEvent();
        unprocessedEvent1.setEventType("PAYMENT_SUBMITTED");
        unprocessedEvent1.setPayload("{\"transactionId\":\"txn_1\"}");
        unprocessedEvent1.setProcessed(false);
        unprocessedEvent1.setCreatedAt(new Date());

        OutboxEvent unprocessedEvent2 = new OutboxEvent();
        unprocessedEvent2.setEventType("PAYMENT_FAILED");
        unprocessedEvent2.setPayload("{\"transactionId\":\"txn_2\"}");
        unprocessedEvent2.setProcessed(false);
        unprocessedEvent2.setCreatedAt(new Date());

        entityManager.persistAndFlush(processedEvent);
        entityManager.persistAndFlush(unprocessedEvent1);
        entityManager.persistAndFlush(unprocessedEvent2);

        List<OutboxEvent> unprocessedEvents = outboxEventRepository.findByProcessedFalse();

        assertEquals(2, unprocessedEvents.size());
        assertTrue(unprocessedEvents.stream().noneMatch(OutboxEvent::isProcessed));
    }

    @Test
    void findByProcessedFalse_NoUnprocessedEvents() {
        OutboxEvent processedEvent = new OutboxEvent();
        processedEvent.setEventType("PAYMENT_CONFIRMED");
        processedEvent.setPayload("{\"transactionId\":\"txn_processed\"}");
        processedEvent.setProcessed(true);
        processedEvent.setCreatedAt(new Date());

        entityManager.persistAndFlush(processedEvent);

        List<OutboxEvent> unprocessedEvents = outboxEventRepository.findByProcessedFalse();

        assertEquals(0, unprocessedEvents.size());
    }
}
