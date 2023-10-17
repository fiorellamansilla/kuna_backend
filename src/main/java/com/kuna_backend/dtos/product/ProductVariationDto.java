package com.kuna_backend.dtos.product;

import com.kuna_backend.enums.Color;
import com.kuna_backend.enums.Size;
import com.kuna_backend.models.ProductVariation;
import org.jetbrains.annotations.NotNull;

public class ProductVariationDto {

    private @NotNull Integer id;
    private Size size;
    private Color color;
    private @NotNull Integer quantityStock;
    private @NotNull Integer productId;

    public ProductVariationDto(ProductVariation productVariation) {
        this.setId(productVariation.getId());
        this.setSize(productVariation.getSize());
        this.setColor(productVariation.getColor());
        this.setQuantityStock(productVariation.getQuantityStock());
        this.setProductId(productVariation.getProduct().getId());
    }

    public ProductVariationDto(Size size, Color color, @NotNull Integer quantityStock, @NotNull Integer productId) {
        this.size = size;
        this.color = color;
        this.quantityStock = quantityStock;
        this.productId = productId;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    // Implemented a method to retrieve all attribute values as an array
    public Object[] getVariations() {
        return new Object[]{id, size, color, quantityStock, productId};
    }
}
