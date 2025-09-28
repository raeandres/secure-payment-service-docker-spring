package com.raeandres.secure_payment_service_docker_spring.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String eventType;
    private String payload;
    private boolean processed = false;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
    
    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
