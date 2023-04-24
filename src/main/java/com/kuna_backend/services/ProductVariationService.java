package com.kuna_backend.services;

import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.repositories.ProductVariationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ProductVariationService {

    @Autowired
    private ProductVariationRepository productVariationRepository;

    public ProductVariation getProductVariationById(Integer productVariationId) throws ProductNotExistsException {
        Optional<Product> optionalProductVariation= productVariationRepository.findById(productVariationId);
        // Check if the product variation exists
        if (optionalProductVariation.isEmpty()) {
            throw new ProductNotExistsException("Product id is invalid:" + productVariationId);
        }
        return (ProductVariation) optionalProductVariation.get().getProductVariations();
    }
}
