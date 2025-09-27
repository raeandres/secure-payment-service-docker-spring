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