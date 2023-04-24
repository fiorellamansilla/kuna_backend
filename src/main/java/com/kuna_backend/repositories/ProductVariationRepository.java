package com.kuna_backend.repositories;

import com.kuna_backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationRepository extends JpaRepository<Product, Integer> {
}