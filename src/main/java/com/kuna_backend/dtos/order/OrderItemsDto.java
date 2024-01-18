package com.kuna_backend.dtos.order;

import org.jetbrains.annotations.NotNull;

public class OrderItemsDto {

    private @NotNull double price;
    private @NotNull int quantity;
    private @NotNull long orderId;
    private @NotNull int productVariationId;

    public OrderItemsDto() {
    }

    public OrderItemsDto(@NotNull double price, @NotNull int quantity, @NotNull long orderId, @NotNull int productVariationId) {
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.productVariationId = productVariationId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(int productVariationId) {
        this.productVariationId = productVariationId;
    }
}
