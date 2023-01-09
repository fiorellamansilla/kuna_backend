package com.kuna_backend.entities;

import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Client {
    @Id
    private Integer id;
    private String username;
    private String passwordClient;
    private String firstName;
    private String lastName;
    private String addressClient;
    private String zipCode;
    private String city;
    private String country;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Client(Integer id,
                  String username,
                  String passwordClient,
                  String firstName,
                  String lastName,
                  String addressClient,
                  String zipCode,
                  String city,
                  String country,
                  String phone,
                  String email,
                  LocalDateTime createdAt,
                  LocalDateTime modifiedAt)
    {
        this.id = Client.this.id;
        this.username = username;
        this.passwordClient = passwordClient;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressClient = addressClient;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = Client.this.id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordClient() {
        return passwordClient;
    }

    public void setPasswordClient(String passwordClient) {
        this.passwordClient = passwordClient;
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

    public String getAddressClient() {
        return addressClient;
    }

    public void setAddressClient(String addressClient) {
        this.addressClient = addressClient;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordClient='" + passwordClient + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressClient='" + addressClient + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
