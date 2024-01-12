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
    public ProductVariation getProductVariationById(Integer productVariationId) throws ProductNotExistsException {
        Optional<ProductVariation> optionalProductVariation = productVariationRepository.findById(productVariationId);
        // Check if the product variation exists
        if (optionalProductVariation.isEmpty()) {
            throw new ProductNotExistsException("The Product Variation id is invalid: " + productVariationId);
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
    public ProductVariation updateProductVariation(Integer productVariationId, ProductVariationDto updatedVariationDto) {

        ProductVariation existingProductVariation = getProductVariationById(productVariationId);

        /* Update only the attributes quantityStock or color from the existing Product Variation */
        if (updatedVariationDto.getQuantityStock() != null){
            existingProductVariation.setQuantityStock(updatedVariationDto.getQuantityStock());
        }
        if (updatedVariationDto.getColor() != null){
            existingProductVariation.setColor(updatedVariationDto.getColor());
        }

        return productVariationRepository.save(existingProductVariation);
    }

    public void deleteProductVariation (Integer id) {
        productVariationRepository.deleteById(id);
    }
}
