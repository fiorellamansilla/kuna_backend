package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.services.ProductService;
import com.kuna_backend.services.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
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

    // GET All Product Variations - Endpoint
    @GetMapping(path = "/all")
    public ResponseEntity<List<ProductVariationDto>> getProductVariations() {
        List<ProductVariationDto> body = productVariationService.listProductVariations();
        return new ResponseEntity<List<ProductVariationDto>>(body, HttpStatus.OK);
    }

    // GET a Product Variation by ID - Endpoint
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductVariation> getProductVariationById(@PathVariable Integer id) {
        try {
            ProductVariation productVariation = productVariationService.getProductVariationById(id);
            return new ResponseEntity<ProductVariation>(productVariation, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<ProductVariation>(HttpStatus.NOT_FOUND);
        }
    }
}
