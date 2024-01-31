package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.dtos.product.ProductDto;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.models.Category;
import com.kuna_backend.models.Product;
import com.kuna_backend.services.CategoryService;
import com.kuna_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    // GET All Products / Endpoint
    @GetMapping(path = "/all")
    public ResponseEntity<List<ProductDto>> getProducts(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductDto> body = productService.listProducts(pageNumber, pageSize);
        return new ResponseEntity<List<ProductDto>>(body, HttpStatus.OK);
    }

    // TODO: TEST GET ENDPOINT TO VERIFY IF THE PRODUCT VARIATION ARRAYLIST IS RETRIEVED
    //GET a Product by ID with its Variations / Endpoint
    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> getProductByIdWithVariations(@PathVariable Long id) {
        try {
            Product product = productService.getProductByIdWithVariations(id);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE a Product / Endpoint
    @PostMapping(path = "/create")
    public ResponseEntity<ApiResponse> createProduct (@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "The category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.createProduct(productDto, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "The Product has been created"), HttpStatus.CREATED);
    }

    // TODO: TEST THIS POST ENDPOINT TO VERIFY IF PRODUCT VARIATION HAS BEEN CORRECTLY ASSIGNED TO A PRODUCT.
    // CREATE a Product Variation for a specific Product when updating it - Endpoint
    @PostMapping(path = "/{productId}/variations")
    public ResponseEntity<ApiResponse> createProductVariationForProduct(@PathVariable("productId") Long productId,
                                                                        @RequestBody ProductVariationDto productVariationDto) {
        Product updatedProduct = productService.createProductVariationForProduct(productId, productVariationDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,
                "Product Variation has been created and the Product has been updated"), HttpStatus.OK);

    }

    // TODO: TEST THIS POST ENDPOINT TO VERIFY THAT ONLY ATTRIBUTES FROM THE PRODUCT HAVE BEEN UPDATED.
    // UPDATE only the attributes from a specific Product by ID - Endpoint
    @PostMapping(path = "/update/{productId}")
    public ResponseEntity<ApiResponse> updateProductOnly(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category NOT found"), HttpStatus.NOT_FOUND);
        }
        Category category = optionalCategory.get();
        productService.updateProductOnly(productId, productDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "The Product has been updated"), HttpStatus.OK);
    }


    //DELETE one Product by ID / Endpoint
    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<ApiResponse> deleteProductById (@PathVariable("productId") Long productId) {

        if(productId <= 0){
            return new ResponseEntity<>(new ApiResponse(false, "Invalid product ID"), HttpStatus.BAD_REQUEST);
        }

        // Attempt to delete the product
        boolean deletionSuccessful = productService.deleteProduct(productId);

        if (deletionSuccessful) {
            return new ResponseEntity<>(new ApiResponse(true, "The Product has been successfully deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Product not found"), HttpStatus.NOT_FOUND);
        }
    }

}
