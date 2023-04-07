package com.kuna_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

import java.util.Date;

@Entity
@Table(name = "shipping_detail")
public class ShippingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", length = 64, nullable = false)
    private String fullName;

    @Column(name = "address", length = 128, nullable = false)
    private String address;

    @Column(name = "zip_code", length = 64, nullable = false)
    private String zipCode;

    @Column(name = "city", length = 32, nullable = false)
    private String city;

    @Column(name = "country", length = 32, nullable = false)
    private String country;

    @Column(name = "phone", length = 32, nullable = false)
    private String phone;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    // One-to-One relationship with Client //
    @OneToOne
    @JoinColumn (name = "client_id", referencedColumnName = "id")
    @JsonIgnore
    private Client client;

    // One-to-One relationship with Order //
    @OneToOne
    @JoinColumn (name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;

    public ShippingDetail() {
    }


    public ShippingDetail(String fullName, String address, String zipCode, String city, String country, String phone) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
