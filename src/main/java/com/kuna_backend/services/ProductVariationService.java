package com.kuna_backend.services;

import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.repositories.ProductVariationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductVariationService {

    @Autowired
    private ProductVariationRepository productVariationRepository;

    public static ProductVariationDto getDtoFromProductVariation(ProductVariation productVariation) {
        ProductVariationDto productVariationDto = new ProductVariationDto(productVariation);
        return productVariationDto;
    }

    // Get a specific Product Variation by ID method
    public ProductVariation getProductVariationById(long productVariationId) throws ProductNotExistsException {
        Optional<ProductVariation> optionalProductVariation = productVariationRepository.findById(productVariationId);
        // Check if the product variation exists
        if (optionalProductVariation.isEmpty()) {
            throw new ProductNotExistsException("Product Variation not found with ID: " + productVariationId);
        }
        return optionalProductVariation.get();
    }

    // List All Product Variations method for GET endpoint
    public List<ProductVariationDto> listProductVariations() {
        List<ProductVariation> productVariations = productVariationRepository.findAll();
        List<ProductVariationDto> productVariationDtos = new ArrayList<>();
        for (ProductVariation productVariation: productVariations) {
            ProductVariationDto productVariationDto = getDtoFromProductVariation(productVariation);
            productVariationDtos.add(productVariationDto);
        }
        return productVariationDtos;
    }

    //Update only certain attributes of a specific Product Variation
    @Transactional
    public ProductVariation updateProductVariation(long productVariationId, ProductVariationDto updatedVariationDto) {

        if (updatedVariationDto == null) {
            throw new IllegalArgumentException("ProductVariationDto cannot be null for ID: " + productVariationId);
        }

        ProductVariation existingProductVariation = getProductVariationById(productVariationId);

        if (existingProductVariation == null) {
            // Handle the case where the existing product variation is not found.
            throw new ProductNotExistsException("Product Variation not found with ID: " + productVariationId);
        }

        /* Update only the attributes quantityStock or color from the existing Product Variation */
        if (updatedVariationDto.getQuantityStock() != null){
            existingProductVariation.setQuantityStock(updatedVariationDto.getQuantityStock());
        }

        if (updatedVariationDto.getColor() != null){
            existingProductVariation.setColor(updatedVariationDto.getColor());
        }

        // Save the updated ProductVariation
        return productVariationRepository.save(existingProductVariation);
    }

    // Delete a specific productVariation by Id
    public boolean deleteProductVariation (long productVariationId) {
        Optional<ProductVariation> optionalProductVariation = productVariationRepository.findById(productVariationId);

        if(optionalProductVariation.isPresent()) {
            productVariationRepository.delete(optionalProductVariation.get());
            return true; // Deletion successful
        } else {
            return false; // ProductVariation not found, deletion unsuccessful
        }
    }

}
