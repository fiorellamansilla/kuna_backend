package com.kuna_backend;

import com.kuna_backend.builders.ProductTestDataBuilder;
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
import org.mockito.AdditionalAnswers;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductVariationRepository productVariationRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenProduct_whenGetDtoFromProduct_returnCorrectDto() {

        Category category = ProductTestDataBuilder.buildSampleCategory(1L);
        Product product = ProductTestDataBuilder.buildSampleProduct();
        product.setCategory(category);

        ProductDto productDto = productService.getDtoFromProduct(product);

        assertEquals(product.getId(), productDto.getId());
        assertEquals(product.getName(), productDto.getName());
        assertEquals(product.getPrice(), productDto.getPrice());
        assertEquals(product.getCategory(), category);
    }

    @Test
    public void givenProductDtoAndCategory_whenGetProductFromDto_returnCorrectProduct() {

        ProductDto productDto = ProductTestDataBuilder.buildSampleProductDto();
        Category category = ProductTestDataBuilder.buildSampleCategory(1L);

        Product product = productService.getProductFromDto(productDto, category);

        assertEquals(productDto.getName(), product.getName());
        assertEquals(productDto.getPrice(), product.getPrice());
        assertEquals(category, product.getCategory());
    }

    @Test
    public void givenValidProductId_whenGetProductById_returnProduct() throws ProductNotExistsException {

        Long productId = 1L;
        Product product = ProductTestDataBuilder.buildSampleProduct();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);

        assertEquals(product, result);
    }

    @Test
    public void givenProductDtoAndCategory_whenCreateProduct_thenProductSaved() {

        ProductDto productDto = ProductTestDataBuilder.buildSampleProductDto();
        Category category = ProductTestDataBuilder.buildSampleCategory(1L);

        productService.createProduct(productDto, category);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void givenPageNumberAndPageSize_whenListProducts_returnProductDtoList() {

        int pageNumber = 0;
        int pageSize = 10;

        Category category1 = ProductTestDataBuilder.buildSampleCategory(1L);
        Category category2 = ProductTestDataBuilder.buildSampleCategory(2L);

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
    public void getProductVariationFromDto_ShouldMapDtoToProductVariation() {

        ProductVariationDto productVariationDto = ProductTestDataBuilder.buildSampleProductVariationDto(1L);
        Product product = new Product();

        ProductVariation productVariation = ProductService.getProductVariationFromDto(productVariationDto, product);

        assertEquals(productVariationDto.getSize(), productVariation.getSize());
        assertEquals(productVariationDto.getColor(), productVariation.getColor());
        assertEquals(product, productVariation.getProduct());
    }

    @Test
    public void createProductVariationForProduct_ShouldCreateProductVariationAndUpdateProduct(){

        Long productId = 1L;
        Product existingProduct = ProductTestDataBuilder.buildSampleProduct();
        ProductVariationDto productVariationDto = ProductTestDataBuilder.buildSampleProductVariationDto(1L);
        ProductVariation productVariation = productService.getProductVariationFromDto(productVariationDto, existingProduct);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(productVariationRepository.save(any(ProductVariation.class))).thenReturn(productVariation);

        Product updatedProduct = productService.createProductVariationForProduct(productId, productVariationDto);

        assertNotNull(updatedProduct);
        assertEquals(existingProduct, updatedProduct);

        verify(productRepository, times(1)).findById(productId);
        verify(productVariationRepository, times(1)).save(any(ProductVariation.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void getProductByValidIdWithVariations_ShouldReturnProductWithVariations() throws ProductNotExistsException {

        Long productId = 1L;
        Product product = ProductTestDataBuilder.buildSampleProduct();
        when(productRepository.findByIdWithVariations(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductByIdWithVariations(productId);

        assertEquals(product, result);
    }

    @Test
    public void getProductByInvalidIdWithVariations_ShouldThrowProductNotExistsException() throws ProductNotExistsException {

        Long productId = 1L;
        when(productRepository.findByIdWithVariations(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotExistsException.class, () -> {
            productService.getProductByIdWithVariations(productId);
        });
    }

    @Test
    public void updateProductOnly_ShouldUpdateProductAttributesWithNewData() {

        Long productId = 1L;
        Product originalProduct = ProductTestDataBuilder.buildSampleProduct();
        ProductDto updatedProductDto = ProductTestDataBuilder.buildSampleProductDto();
        when(productRepository.findById(eq(productId))).thenReturn(Optional.of(originalProduct));
        when(productRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Product updatedProduct = productService.updateProductOnly(productId, updatedProductDto);

        assertNotNull(updatedProduct);
        assertEquals(updatedProductDto.getName(), updatedProduct.getName());
        assertEquals(updatedProductDto.getPrice(), updatedProduct.getPrice());
        assertEquals(updatedProductDto.getDescription(), updatedProduct.getDescription());
        assertEquals(updatedProductDto.getImageUrl(), updatedProduct.getImageUrl());
        assertNotNull(updatedProduct.getModifiedAt());

        verify(productRepository, times(1)).findById(eq(productId));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void deleteExistingProduct_ShouldReturnTrue() {

        Long productId = 1L;
        Product mockProduct = ProductTestDataBuilder.buildSampleProduct();
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(mockProduct));

        boolean deletionSuccessful = productService.deleteProduct(productId);

        assertTrue(deletionSuccessful);
        verify(productRepository, times(1)).delete(mockProduct);
    }

    @Test
    public void deleteNonExistingProduct_ShouldReturnFalse() {

        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());

        boolean deletionSuccessful = productService.deleteProduct(productId);

        assertFalse(deletionSuccessful);
        verify(productRepository, never()).delete(any());
    }

    @Test
    public void deleteProductInvalidProductId_ShouldReturnFalse() {

        Long invalidProductId = 0L;

        boolean deletionSuccessful = productService.deleteProduct(invalidProductId);

        assertFalse(deletionSuccessful);
        verify(productRepository, never()).delete(any());
    }

}
