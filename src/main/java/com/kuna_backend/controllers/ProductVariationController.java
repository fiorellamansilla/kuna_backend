package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.services.ProductService;
import com.kuna_backend.services.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/productvariation")
public class ProductVariationController {

    @Autowired
    ProductVariationService productVariationService;
    @Autowired
    ProductService productService;

    // GET a Product Variation by ID - Endpoint
    @GetMapping(path = "/{productVariationId}")
    public ResponseEntity<ProductVariation> getProductVariationById(@PathVariable Long productVariationId) {
        try {
            ProductVariation productVariation = productVariationService.getProductVariationById(productVariationId);
            return new ResponseEntity<ProductVariation>(productVariation, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<ProductVariation>(HttpStatus.NOT_FOUND);
        }
    }

    // GET All Product Variations - Endpoint
    @GetMapping(path = "/all")
    public ResponseEntity<List<ProductVariationDto>> getProductVariations() {
        List<ProductVariationDto> body = productVariationService.listProductVariations();
        return new ResponseEntity<List<ProductVariationDto>>(body, HttpStatus.OK);
    }

    //UPDATE a Product Variation by ID - Put Endpoint
    @PutMapping("/update/{productVariationId}")
    public ResponseEntity<ApiResponse> updateProductVariation (@PathVariable("productVariationId") Long productVariationId,
                                                               @RequestBody ProductVariationDto updatedVariationDto) {
        try{
            productVariationService.updateProductVariation(productVariationId, updatedVariationDto);

            ApiResponse response = new ApiResponse(true, "Product Variation updated successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(false,"ProductVariationDto cannot be null for ID: " + productVariationId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //DELETE one ProductVariation by ID / Endpoint
    @DeleteMapping(path = "/{productVariationId}")
    public ResponseEntity<ApiResponse> deleteProductVariationById (@PathVariable("productVariationId") Long productVariationId) {

        if(productVariationId <= 0){
            return new ResponseEntity<>(new ApiResponse(false, "Invalid product Variation ID"), HttpStatus.BAD_REQUEST);
        }

        boolean deletionSuccessful = productVariationService.deleteProductVariation(productVariationId);

        if (deletionSuccessful) {
            return new ResponseEntity<>(new ApiResponse(true, "The Product Variation has been successfully deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Product Variation not found"), HttpStatus.NOT_FOUND);
        }
    }

}
