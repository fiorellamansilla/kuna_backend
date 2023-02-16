package com.kuna_backend.services;

import com.kuna_backend.entities.Payment;
import com.kuna_backend.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

    public Payment getPayment (Integer id) {
        return paymentRepository.findById(id).get();
    }

    public void createPayment (Payment payment) {
        paymentRepository.save(payment);
    }

    public void deletePayment (Integer id) {
        paymentRepository.deleteById(id);
    }
}
