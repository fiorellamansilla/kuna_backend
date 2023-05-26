package com.kuna_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table (name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name", length = 64, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 64, nullable = false)
    private String lastName;
    @Column(name = "email", length = 64, nullable = false)
    private String email;
    @Column(name = "password", length = 256, nullable = false)
    private String password;
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany (mappedBy = "client", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    @OneToMany (mappedBy = "client", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payment> payments;

    // One-to-One relationship with ShippingDetail//
    @OneToOne(mappedBy = "client")
    @JsonIgnore
    private ShippingDetail shippingDetail;

    public Client() {
    }

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Client(String email, String password) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Payment> getPayments() {
        return payments;
    }
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public ShippingDetail getShippingDetail() {
        return shippingDetail;
    }

    public void setShippingDetail(ShippingDetail shippingDetail) {
        this.shippingDetail = shippingDetail;
    }
}


