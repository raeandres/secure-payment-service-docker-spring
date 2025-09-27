package com.raeandres.secure_payment_service_docker_spring.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "payment-events", groupId = "payment-group")
    public void consume(String message){
        System.out.println("Consumed message" + message);

    }
}
