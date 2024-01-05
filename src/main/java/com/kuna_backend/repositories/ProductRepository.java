package com.kuna_backend.repositories;

import com.kuna_backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, Integer> {

    //Fetches the product along with its variations, in a single query
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productVariations WHERE p.id = :productId")
    Optional<Product> findByIdWithVariations(@Param("productId") Integer productId);
}
