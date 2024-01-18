package com.kuna_backend.dtos.product;

import com.kuna_backend.enums.Color;
import com.kuna_backend.enums.Size;
import com.kuna_backend.models.ProductVariation;
import org.jetbrains.annotations.NotNull;

public class ProductVariationDto {

    private @NotNull Integer id;
    private Integer quantityStock;
    private Size size;
    private Color color;
    private long productId;

    public ProductVariationDto(ProductVariation productVariation) {
        this.setId(productVariation.getId());
        this.setQuantityStock(productVariation.getQuantityStock());
        this.setSize(productVariation.getSize());
        this.setColor(productVariation.getColor());
        this.setProductId(productVariation.getProduct().getId());
    }

    public ProductVariationDto(Integer quantityStock, Size size, Color color, long productId) {
        this.quantityStock = quantityStock;
        this.size = size;
        this.color = color;
        this.productId = productId;
    }

    // Constructor for partial updates
    public ProductVariationDto(Integer id, Integer quantityStock, Color color) {
        this.id = id;
        this.quantityStock = quantityStock;
        this.color = color;
    }

    public ProductVariationDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(Integer quantityStock) {
        this.quantityStock = quantityStock;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    // Implemented a method to retrieve all attribute values as an array
    public Object[] getVariations() {
        return new Object[]{id, size, color, quantityStock, productId};
    }
}
