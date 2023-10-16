package com.kuna_backend.services;

import com.kuna_backend.dtos.product.ProductDto;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.Category;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.repositories.ProductRepository;
import com.kuna_backend.repositories.ProductVariationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariationRepository productVariationRepository;

    // PRODUCT
    public static ProductDto getDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        return product;
    }

    // Create a Product - method //
    public void createProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        productRepository.save(product);
    }

    // Method for GET all Products endpoint with Pagination
    public List<ProductDto> listProducts(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: productPage) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    // Get a specific Product by ID method
    public Product getProductById(Integer productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        // Check if the product exists
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("The Product id is invalid: " + productId);
        }
        return optionalProduct.get();
    }

    // PRODUCT VARIATIONS
    public static ProductVariation getProductVariationFromDto(ProductVariationDto productVariationDto, Product product) {
        ProductVariation productVariation = new ProductVariation(productVariationDto, product);
        return productVariation;
    }

    public Product createProductVariationForProduct (Integer productId, ProductVariationDto productVariationDto) {

        // Retrieve the specific Product based on the ID.
        Product product = getProductById(productId);

        // Create a new Product Variation entity and populate with the corresponding data from ProductVariationDto
        ProductVariation productVariation = getProductVariationFromDto(productVariationDto, product);

        // Set the Product to which the ProductVariation belongs
        productVariation.setProduct(product);

        // Save the new ProductVariation entity in the database, which will generate an unique ID for it.
        productVariation = productVariationRepository.save(productVariation);

        // Add the ProductVariation to the Product's collection
        product.getProductVariations().add(productVariation);

        // Save and return the updated Product
        return productRepository.save(product);
    }

    //TODO: Implement an Update product variations from a Product method. This is necessary because so the stock can get easily updated.

    public void updateProductVariation(Integer productVariationId, ProductVariationDto productVariationDto, Product product) {
        ProductVariation productVariation = getProductVariationFromDto(productVariationDto, product);
        productVariation.setId(productVariationId);
        productVariationRepository.save(productVariation);
    }

    // TODO: Evaluate what to do with these methods from Product.
    // TODO: Implement a method for Updating the Product attributes without the Variations. i.e the price.
    // Update a specific Product by ID method
    public void updateProduct(Integer productId, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productId);
        product.setCreatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        product.setModifiedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        productRepository.save(product);
    }

    public Optional<Product> readProduct(Integer productId) {
        return productRepository.findById(productId);
    }

    public void deleteProduct (Integer id) {
        productRepository.deleteById(id);
    }

}
