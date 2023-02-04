package com.kuna_backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @Column (name = "amount", nullable = false)
    private Float amount = 0.0f;
    @Column (name = "first_name", length = 128, nullable = false)
    private String firstName;
    @Column (name = "last_name", length = 128, nullable = false)
    private String lastName;
    @Column (name = "address", length = 2048, nullable = false)
    private String address;
    @Column (name = "city", length = 32, nullable = false)
    private String city;
    @Column (name = "zip_code", length = 64, nullable = false)
    private String zipCode;
    @Column (name = "country", length = 32, nullable = false)
    private String country;
    @Column (name = "phone", length = 32, nullable = false)
    private String phone;
    @Column (name = "email", length = 64, nullable = false)
    private String email;
    @Column (name = "ordered_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime orderedAt;
    @Column (name = "shipped_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime shippedAt;
    @Column (name = "tracking_number", length = 64, nullable = false)
    private String trackingNumber;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "client_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Client client;

    @ManyToMany (mappedBy = "orders", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonBackReference
    private Set<Item> items = new HashSet<Item>();

    public Order() {
    }

    public Order(Integer id, Float amount, String firstName, String lastName, String address, String city, String zipCode, String country, String phone, String email, LocalDateTime orderedAt, LocalDateTime shippedAt, String trackingNumber, Client client) {
        this.id = id;
        this.amount = amount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.orderedAt = orderedAt;
        this.shippedAt = shippedAt;
        this.trackingNumber = trackingNumber;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public LocalDateTime getShippedAt() {
        return shippedAt;
    }

    public void setShippedAt(LocalDateTime shippedAt) {
        this.shippedAt = shippedAt;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
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
