package com.raeandres.secure_payment_service_docker_spring.domain;

import com.raeandres.secure_payment_service_docker_spring.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findByProcessedFalse();
}
