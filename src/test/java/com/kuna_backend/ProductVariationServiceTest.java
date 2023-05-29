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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

        productVariation.setId(1);
        productVariation.setSize(Size.NEWBORN);
        productVariation.setProduct(product);

        ProductVariationDto productVariationDto = ProductVariationService.getDtoFromProductVariation(productVariation);

        assertEquals(productVariation.getId(), productVariationDto.getId());
        assertEquals(productVariation.getSize(), productVariationDto.getSize());
        assertEquals(productVariation.getProduct(), product);
    }

    @Test
    public void testGetProductVariationFromDto() {

        ProductVariationDto productVariationDto = new ProductVariationDto();
        productVariationDto.setSize(Size.NEWBORN);
        productVariationDto.setColor(Color.BEIGE);

        Product product = new Product();

        ProductVariation productVariation = ProductVariationService.getProductVariationFromDto(productVariationDto, product);

        assertEquals(productVariationDto.getSize(), productVariation.getSize());
        assertEquals(productVariationDto.getColor(), productVariation.getColor());
        assertEquals(product, productVariation.getProduct());
    }

    @Test
    public void createProductVariation_ShouldSaveProductVariation() {

        ProductVariationDto productVariationDto = new ProductVariationDto();
        Product product = new Product();

        productVariationService.createProductVariation(productVariationDto, product);

        verify(productVariationRepository).save(any(ProductVariation.class));
    }

    @Test
    public void listProductVariations_ShouldReturnListOfProductVariationDtos() {

        Product product1 = new Product();
        product1.setId(1);

        Product product2 = new Product();
        product2.setId(2);

        List<ProductVariation> productVariations = new ArrayList<>();
        productVariations.add(new ProductVariation(new ProductVariationDto(), product1));
        productVariations.add(new ProductVariation(new ProductVariationDto(), product2));

        when(productVariationRepository.findAll()).thenReturn(productVariations);

        List<ProductVariationDto> productVariationDtos = productVariationService.listProductVariations();

        assertEquals(productVariations.size(), productVariationDtos.size());
    }

    @Test
    public void getProductVariationById_WithValidId_ShouldReturnProductVariation() throws ProductNotExistsException {

        Integer productVariationId = 1;
        ProductVariation productVariation = new ProductVariation();

        when(productVariationRepository.findById(productVariationId)).thenReturn(Optional.of(productVariation));

        ProductVariation result = productVariationService.getProductVariationById(productVariationId);

        assertEquals(productVariation, result);
    }

    @Test
    public void getProductVariationById_WithInvalidId_ShouldThrowException() {

        Integer productVariationId = 1;

        when(productVariationRepository.findById(productVariationId)).thenReturn(Optional.empty());

        assertThrows(ProductNotExistsException.class,
                () -> productVariationService.getProductVariationById(productVariationId));
    }

    @Test
    public void updateProductVariation_ShouldSaveModifiedProductVariation() {

        Integer productVariationId = 1;
        ProductVariationDto productVariationDto = new ProductVariationDto();
        Product product = new Product();

        Optional<ProductVariation> optionalProductVariation = Optional.of(new ProductVariation(productVariationId, productVariationDto, product));
        when(productVariationRepository.findById(productVariationId)).thenReturn(optionalProductVariation);

        productVariationService.updateProductVariation(productVariationId, productVariationDto, product);

        verify(productVariationRepository).save(any(ProductVariation.class));
    }

    @Test
    public void deleteProductVariation_ShouldCallRepositoryDeleteById() {

        Integer productVariationId = 1;

        productVariationService.deleteProductVariation(productVariationId);

        verify(productVariationRepository, times(1)).deleteById(productVariationId);
    }
}
