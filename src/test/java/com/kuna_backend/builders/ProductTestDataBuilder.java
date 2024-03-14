package com.kuna_backend.builders;

import com.kuna_backend.dtos.product.ProductDto;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.enums.Color;
import com.kuna_backend.enums.Size;
import com.kuna_backend.models.Category;
import com.kuna_backend.models.Product;

public class ProductTestDataBuilder {

    public static Product buildSampleProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Example Product");
        product.setPrice(9.99);
        return product;
    }

    public static Category buildSampleCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        return category;
    }

    public static ProductDto buildSampleProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Example Product");
        productDto.setPrice(9.99);
        return productDto;
    }

    public static ProductVariationDto buildSampleProductVariationDto(Long productId) {
        ProductVariationDto productVariationDto = new ProductVariationDto();
        productVariationDto.setQuantityStock(10);
        productVariationDto.setSize(Size.NEWBORN);
        productVariationDto.setColor(Color.BEIGE);
        productVariationDto.setProductId(productId);
        return productVariationDto;
    }

}
