package com.raeandres.secure_payment_service_docker_spring.domain;

import com.raeandres.secure_payment_service_docker_spring.model.PaymentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void save_Success() {
        PaymentEntity payment = new PaymentEntity();
        payment.setTransactionId("txn_123");
        payment.setAmount(100.0);
        payment.setStatus("SUBMITTED");
        payment.setAccountId("acc_456");
        payment.setCreatedAt(new Date());
        payment.setModifiedAt(new Date());

        PaymentEntity saved = paymentRepository.save(payment);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("txn_123", saved.getTransactionId());
        assertEquals(100.0, saved.getAmount());
    }

    @Test
    void findById_Success() {
        PaymentEntity payment = new PaymentEntity();
        payment.setTransactionId("txn_456");
        payment.setAmount(200.0);
        payment.setStatus("CONFIRMED");
        payment.setCreatedAt(new Date());

        PaymentEntity saved = entityManager.persistAndFlush(payment);
        Optional<PaymentEntity> found = paymentRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("txn_456", found.get().getTransactionId());
        assertEquals(200.0, found.get().getAmount());
    }

    @Test
    void findAll_Success() {
        PaymentEntity payment1 = new PaymentEntity();
        payment1.setTransactionId("txn_1");
        payment1.setAmount(100.0);
        payment1.setStatus("SUBMITTED");
        payment1.setCreatedAt(new Date());

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setTransactionId("txn_2");
        payment2.setAmount(200.0);
        payment2.setStatus("CONFIRMED");
        payment2.setCreatedAt(new Date());

        entityManager.persistAndFlush(payment1);
        entityManager.persistAndFlush(payment2);

        List<PaymentEntity> payments = paymentRepository.findAll();

        assertEquals(2, payments.size());
    }

    @Test
    void delete_Success() {
        PaymentEntity payment = new PaymentEntity();
        payment.setTransactionId("txn_delete");
        payment.setAmount(50.0);
        payment.setStatus("FAILED");
        payment.setCreatedAt(new Date());

        PaymentEntity saved = entityManager.persistAndFlush(payment);
        paymentRepository.delete(saved);

        Optional<PaymentEntity> found = paymentRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }
}
