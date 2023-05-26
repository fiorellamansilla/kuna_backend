package com.kuna_backend;

import com.kuna_backend.dtos.product.ProductDto;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.Category;
import com.kuna_backend.models.Product;
import com.kuna_backend.repositories.ProductRepository;
import com.kuna_backend.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDtoFromProduct() {

        Product product = new Product();
        Category category = new Category();

        product.setId(1);
        product.setName("Example Product");
        product.setPrice(9.99);
        product.setCategory(category);

        // Call the getDtoFromProduct method
        ProductDto productDto = ProductService.getDtoFromProduct(product);

        // Verify that the returned ProductDto has the expected properties
        assertEquals(product.getId(), productDto.getId());
        assertEquals(product.getName(), productDto.getName());
        assertEquals(product.getPrice(), productDto.getPrice());
        assertEquals(product.getCategory(), category);
    }

    @Test
    public void testGetProductFromDto() {

        ProductDto productDto = new ProductDto();
        productDto.setName("Example Product");
        productDto.setPrice(9.99);

        Category category = new Category();

        Product product = ProductService.getProductFromDto(productDto, category);

        assertEquals(productDto.getName(), product.getName());
        assertEquals(productDto.getPrice(), product.getPrice());
        assertEquals(category, product.getCategory());
    }

    @Test
    public void createProduct_ShouldSaveProduct() {

        ProductDto productDto = new ProductDto();
        Category category = new Category();

        productService.createProduct(productDto, category);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void listProducts_ShouldReturnListOfProductDtos() {

        Integer pageNumber = 0;
        Integer pageSize = 10;

        Category category1 = new Category();
        category1.setId(1);
        Category category2 = new Category();
        category2.setId(2);

        List<Product> products = new ArrayList<>();
        products.add(new Product(new ProductDto(), category1));
        products.add(new Product(new ProductDto(), category2));

        Page<Product> productPage = new PageImpl<>(products);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

        List<ProductDto> result = productService.listProducts(pageNumber, pageSize);

        assertEquals(2, result.size());
        assertNotNull(result.get(0).getCategoryId());
        assertNotNull(result.get(1).getCategoryId());
    }

    @Test
    public void getProductById_WithValidId_ShouldReturnProduct() throws ProductNotExistsException {

        Integer productId = 1;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);

        assertEquals(product, result);
    }

    @Test
    public void getProductById_WithInvalidId_ShouldThrowException() {

        Integer productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotExistsException.class, () -> {
            productService.getProductById(productId);
        });
    }

    @Test
    public void updateProduct_ShouldSaveModifiedProduct() {

        Integer productId = 1;
        ProductDto productDto = new ProductDto();
        Category category = new Category();

        Optional<Product> optionalProduct = Optional.of(new Product(productId, productDto, category));
        when(productRepository.findById(productId)).thenReturn(optionalProduct);

        productService.updateProduct(productId, productDto, category);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void deleteProduct_ShouldCallRepositoryDeleteById() {

        Integer productId = 1;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}
