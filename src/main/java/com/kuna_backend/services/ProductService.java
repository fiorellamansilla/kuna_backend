package com.kuna_backend.services;

import com.kuna_backend.dtos.product.ProductDto;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.Category;
import com.kuna_backend.models.Product;
import com.kuna_backend.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

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
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product getProductById(Integer productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        // Check if the product exists
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("Product id is invalid:" + productId);
        }
        return optionalProduct.get();
    }

    public Optional<Product> readProduct(Integer productId) {
        return productRepository.findById(productId);
    }

    public void deleteProduct (Integer id) {
        productRepository.deleteById(id);
    }

}
