package com.raeandres.secure_payment_service_docker_spring.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.kafka.bootstrap-servers=localhost:9092",
    "spring.redis.host=localhost",
    "spring.redis.port=6379"
})
class PaymentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void submitPayment_IntegrationTest() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setTransactionId("integration_txn_123");
        request.setReferenceId("integration_ref_456");
        request.setAmount("150.75");
        request.setAccountFrom("integration_acc_from");
        request.setAccountTo("integration_acc_to");

        mockMvc.perform(post("/payment/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void confirmPayment_IntegrationTest() throws Exception {
        mockMvc.perform(post("/payment/confirm"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment Confirmed!"));
    }

    @Test
    void getPayments_IntegrationTest() throws Exception {
        mockMvc.perform(get("/payment/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
