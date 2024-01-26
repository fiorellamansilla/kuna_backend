package com.kuna_backend.dtos.order;

import org.jetbrains.annotations.NotNull;

public class OrderItemsDto {

    private @NotNull double price;
    private @NotNull int quantity;
    private @NotNull Long orderId;
    private @NotNull Long productVariationId;

    public OrderItemsDto() {
    }

    public OrderItemsDto(@NotNull double price, @NotNull int quantity, @NotNull Long orderId, Long productVariationId) {
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Long productVariationId) {
        this.productVariationId = productVariationId;
    }
}
