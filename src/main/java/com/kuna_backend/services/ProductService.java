package com.kuna_backend.services;

import com.kuna_backend.exceptions.ProductNotExistsException;
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

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product getProduct (Integer id) {
        return productRepository.findById(id).get();
    }

    public void createProduct (Product product) {
        productRepository.save(product);
    }

    public void deleteProduct (Integer id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Integer productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct= productRepository.findById(productId);
        // Check if the product exists
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("Product id is invalid:" + productId);
        }
        return optionalProduct.get();
    }
}
