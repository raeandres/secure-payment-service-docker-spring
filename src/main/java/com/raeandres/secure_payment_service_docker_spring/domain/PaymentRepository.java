package com.raeandres.secure_payment_service_docker_spring.domain;

import com.raeandres.secure_payment_service_docker_spring.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
