package com.kuna_backend.dtos.product;

import com.kuna_backend.enums.Color;
import com.kuna_backend.enums.Size;

public class ProductVariationDto {

    private Integer id;

    private Size size;

    private Color color;

    private Integer quantityStock;

    private Integer productId;

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
