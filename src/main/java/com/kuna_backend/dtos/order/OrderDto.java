package com.kuna_backend.dtos.order;

import com.kuna_backend.models.Order;
import org.jetbrains.annotations.NotNull;

public class OrderDto {
    private Long id;
    private @NotNull Long clientId;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(order.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
