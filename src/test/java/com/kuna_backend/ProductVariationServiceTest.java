package com.kuna_backend;

import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.enums.Color;
import com.kuna_backend.enums.Size;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.repositories.ProductVariationRepository;
import com.kuna_backend.services.ProductVariationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ProductVariationServiceTest {

    @Mock
    private ProductVariationRepository productVariationRepository;

    @InjectMocks
    private ProductVariationService productVariationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDtoFromProductVariation() {

        ProductVariation productVariation = new ProductVariation();
        Product product = new Product();

        productVariation.setId(1L);
        productVariation.setSize(Size.NEWBORN);
        productVariation.setProduct(product);

        ProductVariationDto productVariationDto = ProductVariationService.getDtoFromProductVariation(productVariation);

        assertEquals(productVariation.getId(), productVariationDto.getId());
        assertEquals(productVariation.getSize(), productVariationDto.getSize());
        assertEquals(productVariation.getProduct(), product);
    }

    @Test
    public void listProductVariations_ShouldReturnListOfProductVariationDtos() {

        Product product1 = new Product();
        product1.setId(1L);

        Product product2 = new Product();
        product2.setId(2L);

        List<ProductVariation> productVariations = new ArrayList<>();
        productVariations.add(new ProductVariation(new ProductVariationDto(), product1));
        productVariations.add(new ProductVariation(new ProductVariationDto(), product2));

        when(productVariationRepository.findAll()).thenReturn(productVariations);

        List<ProductVariationDto> productVariationDtos = productVariationService.listProductVariations();

        assertEquals(productVariations.size(), productVariationDtos.size());
    }

    @Test
    public void getProductVariationById_WithValidId_ShouldReturnProductVariation() throws ProductNotExistsException {

        Long productVariationId = 1L;
        ProductVariation productVariation = new ProductVariation();

        when(productVariationRepository.findById(productVariationId)).thenReturn(Optional.of(productVariation));

        ProductVariation result = productVariationService.getProductVariationById(productVariationId);

        assertEquals(productVariation, result);
    }

    @Test
    public void getProductVariationById_WithInvalidId_ShouldThrowException() {

        Long productVariationId = 1L;

        when(productVariationRepository.findById(productVariationId)).thenReturn(Optional.empty());

        assertThrows(ProductNotExistsException.class,
                () -> productVariationService.getProductVariationById(productVariationId));
    }

    @Test
    public void updateProductVariation_SuccessfulUpdate() {

        Long productVariationId = 1L;
        ProductVariation existingProductVariation = new ProductVariation();
        existingProductVariation.setId(productVariationId);
        existingProductVariation.setQuantityStock(5);
        existingProductVariation.setColor(Color.BEIGE);

        ProductVariationDto updatedVariationDto = new ProductVariationDto();
        updatedVariationDto.setQuantityStock(10);

        // Only the quantityStock will be updated. Color is not updated in this test
        when(productVariationRepository.save(eq(existingProductVariation))).thenReturn(existingProductVariation);
        when(productVariationRepository.findById(productVariationId)).thenReturn(Optional.of(existingProductVariation));

        ProductVariation updatedProductVariation = productVariationService.updateProductVariation(productVariationId, updatedVariationDto);

        assertNotNull(updatedProductVariation);
        assertEquals(existingProductVariation.getQuantityStock(), updatedProductVariation.getQuantityStock()); // Check the updated attribute
        assertEquals(existingProductVariation.getColor(), updatedProductVariation.getColor()); // Check that the color remains the same

        verify(productVariationRepository, times(1)).findById(productVariationId);
        // Verify that the productVariationRepository.save method was called with the correct argument
        verify(productVariationRepository, times(1)).save(updatedProductVariation);
    }

    @Test
    public void updateProductVariation_ProductNotExistsException() {

        Long productVariationId = 1L;
        ProductVariationDto updatedVariationDto = new ProductVariationDto();

        when(productVariationRepository.findById(productVariationId)).thenReturn(Optional.empty());

        ProductNotExistsException exception = assertThrows(ProductNotExistsException.class,
                () -> productVariationService.updateProductVariation(productVariationId, updatedVariationDto));

        assertEquals("Product Variation not found with ID: " + productVariationId, exception.getMessage());

        verify(productVariationRepository, times(1)).findById(productVariationId);
        verify(productVariationRepository, never()).save(any(ProductVariation.class));
    }

    @Test
    void updateProductVariation_NullProductVariationDto() {

        Long productVariationId = 1L;

        assertThrows(IllegalArgumentException.class, () -> {
            productVariationService.updateProductVariation(productVariationId, null);
        });
    }


    @Test
    public void deleteProductVariation_shouldReturnTrueForExistingProductVariation() {

        Long productVariationId = 1L;
        ProductVariation productVariation = new ProductVariation();

        when(productVariationRepository.findById(productVariationId)).thenReturn(java.util.Optional.of(productVariation));

        boolean deletionSuccessful = productVariationService.deleteProductVariation(productVariationId);

        assertTrue(deletionSuccessful);

        verify(productVariationRepository, times(1)).delete(productVariation);
    }

    @Test
    public void deleteProductVariation_shouldReturnFalseForNonExistingProduct() {

        Long productVariationId = 1L;

        when(productVariationRepository.findById(productVariationId)).thenReturn(java.util.Optional.empty());

        boolean deletionSuccessful = productVariationService.deleteProductVariation(productVariationId);

        assertFalse(deletionSuccessful);

        verify(productVariationRepository, never()).delete(any());
    }

    @Test
    public void deleteProductVariation_shouldReturnFalseForInvalidProductVariationId() {

        Long invalidProductVariationId = 0L;

        boolean deletionSuccessful = productVariationService.deleteProductVariation(invalidProductVariationId);
        assertFalse(deletionSuccessful);

        verify(productVariationRepository, never()).delete(any());
    }

}
