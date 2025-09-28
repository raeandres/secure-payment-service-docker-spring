# secure-payment-service-docker-spring
A simple payment micro service built on spring containerized through docker 


## Sample Request
curl -X POST http://localhost:8080/payments \
-H "Content-Type: application/json" \
-d '{
"transactionId": "txn_12345",
"amount": 50.75,
"status": "PROCESSING"
}'


## Outbox Pattern Implementation

What was created:

1. OutboxEvent Entity - Stores events before publishing to Kafka
2. OutboxEventRepository - JPA repository for outbox events
3. PaymentEvent - Event payload structure
4. OutboxService - Handles saving events and publishing to Kafka
5. Updated SubmitPaymentService - Now uses transactional outbox pattern
6. Database migration - Creates the outbox_events table

How it works:

1. Transaction Safety: When SubmitPaymentService.execute() is called:
   • Payment is saved to database
   • Event is saved to outbox table
   • Both operations happen in same transaction (@Transactional)

2. Event Publishing: OutboxService runs every 5 seconds:
   • Finds unprocessed events
   • Publishes them to Kafka
   • Marks them as processed

3. Reliability: If Kafka is down, events remain in outbox until successfully published

Key Benefits:
• **Consistency**: Payment and event are saved atomically
• **Reliability**: Events won't be lost if Kafka is unavailable
• **Retry Logic**: Automatic retry through scheduled publishing

Usage: Your existing payment submission will now automatically publish events to Kafka topic payments-events while maintaining data consistency.

The outbox pattern ensures that your payment data and Kafka events stay in sync, even during system failures.