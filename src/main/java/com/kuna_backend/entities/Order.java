package com.kuna_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuna_backend.entities.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column (name = "total_amount", nullable = false)
    private Float totalAmount = 0.0f;
    @Column (name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered;
    @Column (name = "order_status", length = 64, nullable = false)
    @Enumerated (EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;
    @Column (name = "tracking_number", length = 64, nullable = false)
    private String trackingNumber;
    @Column (name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column (name = "modified_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "client_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Client client;

    @ManyToMany (mappedBy = "orders", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<Item> items = new HashSet<Item>();

    public Order() {
    }

    public Order(Integer id, Float totalAmount, Integer quantityOrdered, OrderStatus orderStatus, String trackingNumber, LocalDateTime createdAt, LocalDateTime modifiedAt, Client client) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.quantityOrdered = quantityOrdered;
        this.orderStatus = orderStatus;
        this.trackingNumber = trackingNumber;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

}
