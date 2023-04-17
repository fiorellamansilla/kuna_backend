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

import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column (name = "total_amount", nullable = false)
    private Double totalAmount;
    @Column (name = "order_status", length = 64)
    @Enumerated (EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;
    @Column (name = "session_id", length = 256, nullable = false)
    private String sessionId;
    @Column (name = "tracking_number", length = 64)
    private String trackingNumber;
    @Column (name = "created_at", nullable = false)
    private Date createdAt;
    @Column (name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    // Many-to-One relationship with Client //
    @ManyToOne ()
    @JoinColumn (name = "client_id", referencedColumnName = "id")
    @JsonIgnore
    private Client client;

    // One-to-Many relationship with OrderItem //
    @OneToMany (mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    // One-to-One relationship with Payment //
    @OneToOne (mappedBy = "order", optional = false)
    @JsonIgnore
    private Payment payment;

    // One-to-One relationship with ShippingDetail//
    @OneToOne(mappedBy = "order", optional = false)
    @JsonIgnore
    private ShippingDetail shippingDetail;

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
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

    public ShippingDetail getShippingDetail() {
        return shippingDetail;
    }

    public void setShippingDetail(ShippingDetail shippingDetail) {
        this.shippingDetail = shippingDetail;
    }
}
