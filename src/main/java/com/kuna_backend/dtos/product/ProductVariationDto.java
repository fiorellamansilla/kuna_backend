package com.kuna_backend.dtos.product;

import com.kuna_backend.enums.Color;
import com.kuna_backend.enums.Size;
import com.kuna_backend.models.ProductVariation;

public class ProductVariationDto {

    private Long id;
    private int quantityStock;
    private Size size;
    private Color color;
    private Long productId;

    public ProductVariationDto(ProductVariation productVariation) {
        this.setId(productVariation.getId());
        this.setQuantityStock(productVariation.getQuantityStock());
        this.setSize(productVariation.getSize());
        this.setColor(productVariation.getColor());
        this.setProductId(productVariation.getProduct().getId());
    }

    public ProductVariationDto(int quantityStock, Size size, Color color, Long productId) {
        this.quantityStock = quantityStock;
        this.size = size;
        this.color = color;
        this.productId = productId;
    }

    // Constructor for partial updates
    public ProductVariationDto(Long id, int quantityStock, Color color) {
        this.id = id;
        this.quantityStock = quantityStock;
        this.color = color;
    }

    public ProductVariationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // Implemented a method to retrieve all attribute values as an array
    public Object[] getVariations() {
        return new Object[]{id, size, color, quantityStock, productId};
    }
}
