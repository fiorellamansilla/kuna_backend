package com.kuna_backend.services;

import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.repositories.ProductVariationRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public static ProductVariation getProductVariationFromDto(ProductVariationDto productVariationDto, Product product) {
        ProductVariation productVariation = new ProductVariation(productVariationDto, product);
        return productVariation;
    }

    // Create Product Variation - method //
    public void createProductVariation (ProductVariationDto productVariationDto, Product product) {
        ProductVariation productVariation = getProductVariationFromDto(productVariationDto, product);
        productVariationRepository.save(productVariation);
    }

    public List<ProductVariationDto> listProductVariations() {
        List<ProductVariation> productVariations = productVariationRepository.findAll();
        List<ProductVariationDto> productVariationDtos = new ArrayList<>();
        for (ProductVariation productVariation: productVariations) {
            ProductVariationDto productVariationDto = getDtoFromProductVariation(productVariation);
            productVariationDtos.add(productVariationDto);
        }
        return productVariationDtos;
    }

    public ProductVariation getProductVariationById(Integer productVariationId) throws ProductNotExistsException {
        Optional<ProductVariation> optionalProductVariation = productVariationRepository.findById(productVariationId);
        // Check if the product variation exists
        if (optionalProductVariation.isEmpty()) {
            throw new ProductNotExistsException("The Product Variation id is invalid: " + productVariationId);
        }
        return optionalProductVariation.get();
    }

    public void deleteProductVariation (Integer id) {
        productVariationRepository.deleteById(id);
    }
}
