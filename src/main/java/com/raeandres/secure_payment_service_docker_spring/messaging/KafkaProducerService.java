package com.raeandres.secure_payment_service_docker_spring.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static final String TOPIC = "payments-events";

    public void setKafkaTemplate(String message) {
        this.kafkaTemplate.send(TOPIC, message);
    }
}
