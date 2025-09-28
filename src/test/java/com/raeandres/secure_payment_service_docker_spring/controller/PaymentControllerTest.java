package com.raeandres.secure_payment_service_docker_spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raeandres.secure_payment_service_docker_spring.model.PaymentRequest;
import com.raeandres.secure_payment_service_docker_spring.service.ConfirmPaymentService;
import com.raeandres.secure_payment_service_docker_spring.service.GetPaymentService;
import com.raeandres.secure_payment_service_docker_spring.service.SubmitPaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubmitPaymentService submitPaymentService;

    @MockBean
    private ConfirmPaymentService confirmPaymentService;

    @MockBean
    private GetPaymentService getPaymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void submitPayment_Success() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setTransactionId("txn_123");
        request.setAmount("100.00");
        
        when(submitPaymentService.execute(any(PaymentRequest.class)))
                .thenReturn(ResponseEntity.ok("Payment Submitted!"));

        mockMvc.perform(post("/payment/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment Submitted!"));
    }

    @Test
    void confirmPayment_Success() throws Exception {
        when(confirmPaymentService.execute(null))
                .thenReturn(ResponseEntity.ok("Payment Confirmed!"));

        mockMvc.perform(post("/payment/confirm"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment Confirmed!"));
    }

    @Test
    void getPayments_Success() throws Exception {
        ArrayList<String> payments = new ArrayList<>();
        payments.add("Payment 1");
        payments.add("Payment 2");
        
        when(getPaymentService.execute(null))
                .thenReturn(ResponseEntity.ok(payments));

        mockMvc.perform(get("/payment/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
