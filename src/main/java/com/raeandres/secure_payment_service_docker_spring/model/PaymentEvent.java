package com.raeandres.secure_payment_service_docker_spring.model;

public class PaymentEvent {
    private String transactionId;
    private String status;
    private Double amount;
    private String accountId;

    public PaymentEvent(String transactionId, String status, Double amount, String accountId) {
        this.transactionId = transactionId;
        this.status = status;
        this.amount = amount;
        this.accountId = accountId;
    }

    public String getTransactionId() { return transactionId; }
    public String getStatus() { return status; }
    public Double getAmount() { return amount; }
    public String getAccountId() { return accountId; }
}
