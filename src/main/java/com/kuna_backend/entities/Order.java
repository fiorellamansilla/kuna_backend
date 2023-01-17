package com.kuna_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table (name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = Order.this.id;
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", amount=" + amount +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", orderedAt=" + orderedAt +
                ", shippedAt=" + shippedAt +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }
}
