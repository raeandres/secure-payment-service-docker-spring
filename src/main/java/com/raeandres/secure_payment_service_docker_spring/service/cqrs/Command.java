package com.raeandres.secure_payment_service_docker_spring.service.cqrs;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface Command <I, O>{
    @Transactional
    ResponseEntity<O> execute(I input);
}
