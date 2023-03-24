package com.kuna_backend.dtos.order;

import com.kuna_backend.models.Order;
import org.jetbrains.annotations.NotNull;

public class OrderDto {
    private Integer id;
    private @NotNull Integer clientId;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(order.getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
