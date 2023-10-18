package com.kuna_backend.services;

import com.kuna_backend.dtos.product.ProductDto;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.Category;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.repositories.ProductRepository;
import com.kuna_backend.repositories.ProductVariationRepository;
import jakarta.persistence.EntityNotFoundException;
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

    // PRODUCT VARIATION
    public static ProductVariation getProductVariationFromDto(ProductVariationDto productVariationDto, Product product) {
        ProductVariation productVariation = new ProductVariation(productVariationDto, product);
        return productVariation;
    }

    // Creates a Product Variation assigned to a specific Product when updating it.
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

    // Update only the attributes of a specific Product Variation associated with a Product
    public Product updateProductVariation(Integer productId, Integer productVariationId, ProductVariationDto updatedVariationDto) {

        // Retrieve the specific Product
        Product product = getProductById(productId);

        // Retrieve the specific ProductVariation to update
        ProductVariation productVariation = productVariationRepository.findById(productVariationId)
                .orElseThrow(() -> new EntityNotFoundException("ProductVariation not found"));

        // Update the ProductVariation attributes
        productVariation.setSize(updatedVariationDto.getSize());
        productVariation.setColor(updatedVariationDto.getColor());
        productVariation.setQuantityStock(updatedVariationDto.getQuantityStock());

        // Save the updated ProductVariation
        productVariationRepository.save(productVariation);

        // Save and return the updated Product
        return productRepository.save(product);
    }

    // Update only the attributes of the Product
    public Product updateProduct(Integer productId, ProductDto updatedProductDto, Category category) {

        // Retrieve the specific Product
        Product product = getProductById(productId);

        product.setName(updatedProductDto.getName());
        product.setPrice(updatedProductDto.getPrice());
        product.setDescription(updatedProductDto.getDescription());
        product.setImageUrl(updatedProductDto.getImageUrl());
        product.setCreatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        product.setModifiedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        // Save and return the updated Product
        return productRepository.save(product);
    }

    // TODO: Evaluate what to do with these methods from Product.

    public Optional<Product> readProduct(Integer productId) {
        return productRepository.findById(productId);
    }

    public void deleteProduct (Integer id) {
        productRepository.deleteById(id);
    }

}
