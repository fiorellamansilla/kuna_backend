package com.kuna_backend;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.controllers.ProductVariationController;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.services.ProductVariationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductVariationControllerTest {

    @Mock
    private ProductVariationService productVariationService;
    @InjectMocks
    private ProductVariationController productVariationController;

    @Test
    public void getProductVariationById_shouldReturnProductVariationById() {

        Integer productVariationId = 1;
        ProductVariation productVariation = new ProductVariation();
        when(productVariationService.getProductVariationById(productVariationId)).thenReturn(productVariation);

        ResponseEntity<ProductVariation> response = productVariationController.getProductVariationById(productVariationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productVariation, response.getBody());
    }

    @Test
    public void updateProductVariation_shouldReturnSuccessResponse() {

        Integer productVariationId = 1;
        ProductVariationDto updatedVariationDto = new ProductVariationDto();

        ProductVariation productVariation = new ProductVariation();

        when(productVariationService.updateProductVariation(productVariationId, updatedVariationDto)).thenReturn(productVariation);

        ResponseEntity<ApiResponse> responseEntity = productVariationController.updateProductVariation(productVariationId, updatedVariationDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new ApiResponse(true, "Product Variation updated successfully"), responseEntity.getBody());

        verify(productVariationService, times(1)).updateProductVariation(eq(productVariationId), eq(updatedVariationDto));
    }

    @Test
    public void updateProductVariation_shouldReturnBadRequestResponse() {

        Integer productVariationId = 1;
        ProductVariationDto updatedVariationDto = null;

        doThrow(new IllegalArgumentException("ProductVariationDto cannot be null for ID: " + productVariationId))
                .when(productVariationService).updateProductVariation(eq(productVariationId), eq(updatedVariationDto));

        ResponseEntity<ApiResponse> response = productVariationController.updateProductVariation(productVariationId, updatedVariationDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ProductVariationDto cannot be null for ID: " + productVariationId, response.getBody().getMessage());

        verify(productVariationService, times(1)).updateProductVariation(eq(productVariationId), eq(updatedVariationDto));
    }

    @Test
    public void deleteProductVariationById_shouldReturnSuccessResponse() {

        Integer productVariationId = 1;

        when(productVariationService.deleteProductVariation(productVariationId)).thenReturn(true);

        ResponseEntity<ApiResponse> response = productVariationController.deleteProductVariationById(productVariationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ApiResponse(true, "The Product Variation has been successfully deleted"), response.getBody());

        verify(productVariationService, times(1)).deleteProductVariation(eq(productVariationId));
    }

    @Test
    public void deleteProductVariationById_shouldReturnNotFoundResponseForNonExistingVariation() {

        Integer productVariationId = 20;

        when(productVariationService.deleteProductVariation(productVariationId)).thenReturn(false);

        ResponseEntity<ApiResponse> response = productVariationController.deleteProductVariationById(productVariationId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ApiResponse(false, "Product Variation not found"), response.getBody());

        verify(productVariationService, times(1)).deleteProductVariation(eq(productVariationId));
    }

    @Test
    public void deleteProductVariationById_shouldReturnBadRequestResponseForInvalidId() {

        Integer productVariationId = -40;

        ResponseEntity<ApiResponse> response = productVariationController.deleteProductVariationById(productVariationId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(new ApiResponse(false, "Invalid product Variation ID"), response.getBody());

        verify(productVariationService, never()).deleteProductVariation(anyInt());
    }

}