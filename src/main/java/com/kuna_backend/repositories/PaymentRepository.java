package com.kuna_backend.repositories;

import com.kuna_backend.entities.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository <Payment, Integer> {
}
