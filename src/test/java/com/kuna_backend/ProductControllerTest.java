package com.kuna_backend;

import com.kuna_backend.builders.ProductTestDataBuilder;
import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.controllers.ProductController;
import com.kuna_backend.dtos.product.ProductDto;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.models.Category;
import com.kuna_backend.models.Product;
import com.kuna_backend.services.CategoryService;
import com.kuna_backend.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductController productController;

    @Test
    public void listProducts_ShouldReturnListOfProducts() {

        List<ProductDto> productList = new ArrayList<>();
        when(productService.listProducts(0,10)).thenReturn(productList);

        ResponseEntity<List<ProductDto>> response = productController.getProducts(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList, response.getBody());

    }

    @Test
    public void getProductByValidId_ShouldReturnProductWithVariations() {

        Long productId = 1L;
        Product product = new Product();
        when(productService.getProductByIdWithVariations(productId)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProductByIdWithVariations(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void getProductByInvalidIdWithVariations_ShouldReturnNotFound() {

        Long productId = 1L;
        when(productService.getProductByIdWithVariations(productId)).thenThrow(new NoSuchElementException());

        ResponseEntity<Product> response = productController.getProductByIdWithVariations(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createProduct_ShouldReturnSuccessResponse() {

        ProductDto productDto = ProductTestDataBuilder.buildSampleProductDto();
        Category category = ProductTestDataBuilder.buildSampleCategory(1L);
        Optional<Category> optionalCategory = Optional.of(category);
        when(categoryService.readCategory(productDto.getCategoryId())).thenReturn(optionalCategory);

        ResponseEntity<ApiResponse> response = productController.createProduct(productDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new ApiResponse(true, "The Product has been created"), response.getBody());
        verify(productService, times(1)).createProduct(eq(productDto), any(Category.class));
    }

    @Test
    public void createProduct_ShouldReturnConflictResponse() {

        ProductDto productDto = ProductTestDataBuilder.buildSampleProductDto();
        productDto.setCategoryId(90L);
        Optional<Category> optionalCategory = Optional.empty();
        when(categoryService.readCategory(productDto.getCategoryId())).thenReturn(optionalCategory);

        ResponseEntity<ApiResponse> response = productController.createProduct(productDto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(new ApiResponse(false, "The category is invalid"), response.getBody());
        verify(productService, never()).createProduct(any(ProductDto.class), any(Category.class));
    }

    @Test
    public void createProductVariationForProduct_ShouldReturnSuccessResponse() {

        Long productId = 1L;
        ProductVariationDto productVariationDto = ProductTestDataBuilder.buildSampleProductVariationDto(1L);
        Product updatedProduct = ProductTestDataBuilder.buildSampleProduct();
        when(productService.createProductVariationForProduct(productId, productVariationDto)).thenReturn(updatedProduct);

        ResponseEntity<ApiResponse> response = productController.createProductVariationForProduct(productId, productVariationDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ApiResponse(true, "Product Variation has been created and the Product has been updated"), response.getBody());
    }

    @Test
    public void updateProductAttributesOnly_ShouldReturnSuccessResponse() {

        Long productId = 1L;
        ProductDto productDto = ProductTestDataBuilder.buildSampleProductDto();
        productDto.setCategoryId(1L);
        Category category = ProductTestDataBuilder.buildSampleCategory(1L);
        Optional<Category> optionalCategory = Optional.of(category);
        when(categoryService.readCategory(productDto.getCategoryId())).thenReturn(optionalCategory);

        ResponseEntity<ApiResponse> response = productController.updateProductOnly(productId, productDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ApiResponse(true, "The Product has been updated"), response.getBody());
        verify(productService, times(1)).updateProductOnly(eq(productId), eq(productDto));
    }

    @Test
    public void updateProductOnlyWithInvalidCategory_ShouldReturnNotFoundResponse() {

        Long productId = 1L;
        ProductDto productDto = ProductTestDataBuilder.buildSampleProductDto();
        productDto.setCategoryId(99L);
        Optional<Category> optionalCategory = Optional.empty();
        when(categoryService.readCategory(productDto.getCategoryId())).thenReturn(optionalCategory);

        ResponseEntity<ApiResponse> response = productController.updateProductOnly(productId, productDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ApiResponse(false, "Category NOT found"), response.getBody());
        verify(productService, never()).updateProductOnly(anyLong(), any(ProductDto.class));
    }

    @Test
    public void deleteProductByValidId_ShouldReturnSuccessResponse() {

        Long productId = 1L;
        when(productService.deleteProduct(productId)).thenReturn(true);

        ResponseEntity<ApiResponse> response = productController.deleteProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ApiResponse(true, "The Product has been successfully deleted"), response.getBody());
        verify(productService, times(1)).deleteProduct(eq(productId));
    }

    @Test
    public void deleteProductById_ShouldReturnNotFoundResponse() {

        Long productId = 2L;
        when(productService.deleteProduct(productId)).thenReturn(false);

        ResponseEntity<ApiResponse> response = productController.deleteProductById(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ApiResponse(false, "Product not found"), response.getBody());
        verify(productService, times(1)).deleteProduct(eq(productId));
    }

    @Test
    public void deleteProductWithInvalidId_ShouldReturnBadRequestResponse() {

        Long productId = -90L;

        ResponseEntity<ApiResponse> response = productController.deleteProductById(productId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(new ApiResponse(false, "Invalid product ID"), response.getBody());
        verify(productService, never()).deleteProduct(anyLong());
    }

}
