package com.kuna_backend.dtos.order;

import com.kuna_backend.models.Order;
import org.jetbrains.annotations.NotNull;

public class OrderDto {
    private long id;
    private @NotNull long clientId;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(order.getId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}
