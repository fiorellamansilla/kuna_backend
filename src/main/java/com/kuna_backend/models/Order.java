package com.kuna_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuna_backend.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table (name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column (name = "total_amount", nullable = false)
    private Float totalAmount = 0.0f;
    @Column (name = "order_status", length = 64, nullable = false)
    @Enumerated (EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;
    @Column (name = "session_id", length = 256, nullable = false)
    private String sessionId;
    @Column (name = "tracking_number", length = 64, nullable = false)
    private String trackingNumber;
    @Column (name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column (name = "modified_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    // Many-to-One relationship with Client //
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "client_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Client client;

    // One-to-Many relationship with OrderItem //
    @OneToMany (mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    // One-to-One relationship with Payment //
    @OneToOne (mappedBy = "order", optional = false)
    @JsonIgnore
    private Payment payment;

    public Order() {
    }

    public Order(Integer id, Float totalAmount, OrderStatus orderStatus, String sessionId, String trackingNumber, LocalDateTime createdAt, LocalDateTime modifiedAt, Client client, Payment payment) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.sessionId = sessionId;
        this.trackingNumber = trackingNumber;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.client = client;
        this.payment = payment;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
