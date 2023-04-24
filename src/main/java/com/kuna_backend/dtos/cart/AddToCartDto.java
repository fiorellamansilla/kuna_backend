package com.kuna_backend.dtos.cart;

public class AddToCartDto {
    private Integer id;
    private Integer productVariationId;
    private Integer quantity;

    public AddToCartDto() {
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", productVariationId=" + productVariationId +
                ", quantity=" + quantity +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Integer productVariationId) {
        this.productVariationId = productVariationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
