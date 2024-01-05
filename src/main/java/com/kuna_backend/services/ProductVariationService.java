package com.kuna_backend.services;

import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.repositories.ProductVariationRepository;
import jakarta.persistence.EntityNotFoundException;
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

    // Get a specific Product Variation by ID method
    public ProductVariation getProductVariationById(Integer productVariationId) throws ProductNotExistsException {
        Optional<ProductVariation> optionalProductVariation = productVariationRepository.findById(productVariationId);
        // Check if the product variation exists
        if (optionalProductVariation.isEmpty()) {
            throw new ProductNotExistsException("The Product Variation id is invalid: " + productVariationId);
        }
        return optionalProductVariation.get();
    }

    // TODO: Refactor the POST endpoint for updating in the Product Variation controller.
    //Update only the attributes of a specific Product Variation associated with a Product
    public ProductVariation updateProductVariation(Integer productVariationId, ProductVariationDto updatedVariationDto) {

        // Retrieve the specific ProductVariation to update
        ProductVariation productVariation = productVariationRepository.findById(productVariationId)
                .orElseThrow(() -> new EntityNotFoundException("ProductVariation not found"));

        // Update the ProductVariation attributes
        productVariation.setSize(updatedVariationDto.getSize());
        productVariation.setColor(updatedVariationDto.getColor());
        productVariation.setQuantityStock(updatedVariationDto.getQuantityStock());

        // Save the updated ProductVariation
        return productVariationRepository.save(productVariation);
    }

    public void deleteProductVariation (Integer id) {
        productVariationRepository.deleteById(id);
    }
}
