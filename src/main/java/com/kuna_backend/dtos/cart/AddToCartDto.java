package com.kuna_backend.dtos.cart;

public class AddToCartDto {
    private long id;
    private long productVariationId;
    private int quantity;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(long productVariationId) {
        this.productVariationId = productVariationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
