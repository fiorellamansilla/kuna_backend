package com.kuna_backend;

import com.kuna_backend.dtos.product.ProductDto;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.Category;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.repositories.ProductRepository;
import com.kuna_backend.repositories.ProductVariationRepository;
import com.kuna_backend.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kuna_backend.enums.Color.BEIGE;
import static com.kuna_backend.enums.Size.NEWBORN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductVariationRepository productVariationRepository;

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
    public void testGetProductById() throws ProductNotExistsException {

        Integer productId = 1;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);

        assertEquals(product, result);
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
    public void testGetProductVariationFromDto() {

        ProductVariationDto productVariationDto = new ProductVariationDto();
        productVariationDto.setSize(NEWBORN);
        productVariationDto.setColor(BEIGE);

        Product product = new Product();

        ProductVariation productVariation = ProductService.getProductVariationFromDto(productVariationDto, product);

        assertEquals(productVariationDto.getSize(), productVariation.getSize());
        assertEquals(productVariationDto.getColor(), productVariation.getColor());
        assertEquals(product, productVariation.getProduct());
    }

    @Test
    public void testCreateProductVariationForProduct(){

        Integer productId = 1;
        Product existingProduct = new Product();

        ProductVariationDto productVariationDto = new ProductVariationDto(NEWBORN, BEIGE, 10, productId);

        // Mock the getProductVariationFromDto method
        ProductVariation productVariation = ProductService.getProductVariationFromDto(productVariationDto, existingProduct);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(productVariationRepository.save(any(ProductVariation.class))).thenReturn(productVariation);

        // Call the method under test
        Product updatedProduct = productService.createProductVariationForProduct(productId, productVariationDto);

        // Verify the interactions
        verify(productRepository, times(1)).findById(productId);
        verify(productVariationRepository, times(1)).save(any(ProductVariation.class));
        verify(productRepository, times(1)).save(any(Product.class));

        // Assert the updatedProduct
        assertNotNull(updatedProduct);
        assertEquals(existingProduct, updatedProduct);
    }

    @Test
    public void getProductByIdWithVariations_WithValidId_ShouldReturnProduct() throws ProductNotExistsException {

        Integer productId = 1;
        Product product = new Product();
        when(productRepository.findByIdWithVariations(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductByIdWithVariations(productId);

        assertEquals(product, result);
    }

    @Test
    public void getProductByIdWithVariations_WithInvalidId_ShouldThrowException() {

        Integer productId = 1;
        when(productRepository.findByIdWithVariations(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotExistsException.class, () -> {
            productService.getProductByIdWithVariations(productId);
        });
    }

    @Test
    public void updateProduct_ShouldSaveModifiedProduct() {

        Integer productId = 1;
        ProductDto productDto = new ProductDto();
        Category category = new Category();

        Optional<Product> optionalProduct = Optional.of(new Product(productId, productDto, category));
        when(productRepository.findById(productId)).thenReturn(optionalProduct);

        productService.updateProductOnly(productId, productDto, category);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_shouldReturnTrueForExistingProduct() {

        Integer productId = 1;
        Product mockProduct = new Product();

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(mockProduct));

        boolean deletionSuccessful = productService.deleteProduct(productId);
        assertTrue(deletionSuccessful);

        // Verify that the productRepository.delete method was called with the correct argument
        verify(productRepository, times(1)).delete(mockProduct);
    }

    @Test
    void deleteProduct_shouldReturnFalseForNonExistingProduct() {

        Integer productId = 1;

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());

        boolean deletionSuccessful = productService.deleteProduct(productId);
        assertFalse(deletionSuccessful);

        // Verify that the productRepository.delete method was not called in this case
        verify(productRepository, never()).delete(any());
    }

    @Test
    void deleteProduct_shouldReturnFalseForInvalidProductId() {

        Integer invalidProductId = null; // Invalid product ID

        boolean deletionSuccessful = productService.deleteProduct(invalidProductId);

        assertFalse(deletionSuccessful);

        // Verify that the productRepository.delete method was not called in this case
        verify(productRepository, never()).delete(any());
    }

}
