package com.raeandres.secure_payment_service_docker_spring.model;


import org.springframework.data.annotation.Id;

public record PaymentRequest(String referenceId,
                             String accountId,

                             String transactionId,
                             String accountTo,
                             String accountFrom,
                             String amount,
                             String createdAt,
                             String modifiedAt) {
}
