package com.raeandres.secure_payment_service_docker_spring.domain;

import com.raeandres.secure_payment_service_docker_spring.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
