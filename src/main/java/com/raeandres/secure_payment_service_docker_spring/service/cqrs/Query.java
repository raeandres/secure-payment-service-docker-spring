package com.raeandres.secure_payment_service_docker_spring.service.cqrs;

import org.springframework.http.ResponseEntity;

public interface Query <I, O>{
    ResponseEntity<O> execute(I input);
}
