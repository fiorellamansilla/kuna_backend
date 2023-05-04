package com.kuna_backend.dtos.product;

import com.kuna_backend.enums.Color;
import com.kuna_backend.enums.Size;
import com.kuna_backend.models.ProductVariation;

public class ProductVariationDto {

    private Integer id;

    private Size size;

    private Color color;

    private Integer quantityStock;

    private Integer productId;

    public ProductVariationDto(ProductVariation productVariation) {
        this.setId(productVariation.getId());
        this.setSize(productVariation.getSize());
        this.setColor(productVariation.getColor());
        this.setQuantityStock(productVariation.getQuantityStock());
        this.setProductId(productVariation.getProduct().getId());
    }

    public ProductVariationDto(Size size, Color color, Integer quantityStock, Integer productId) {
        this.size = size;
        this.color = color;
        this.quantityStock = quantityStock;
        this.productId = productId;
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
}
