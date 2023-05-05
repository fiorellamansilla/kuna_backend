package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.models.Product;
import com.kuna_backend.services.ProductService;
import com.kuna_backend.services.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/productvariation")
public class ProductVariationController {

    @Autowired
    ProductVariationService productVariationService;
    @Autowired
    ProductService productService;

    // CREATE a Product Variation - Endpoint
    @PostMapping(path = "/create")
    public ResponseEntity<ApiResponse> createProductVariation (@RequestBody ProductVariationDto productVariationDto) {
        Optional<Product> optionalProduct = productService.readProduct(productVariationDto.getProductId());
        if (!optionalProduct.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "The product is invalid"), HttpStatus.CONFLICT);
        }
        Product product = optionalProduct.get();
        productVariationService.createProductVariation(productVariationDto, product);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product Variation has been created"), HttpStatus.CREATED);
    }


}
