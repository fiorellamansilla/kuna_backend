package com.kuna_backend.entities;

import java.time.LocalDateTime;

public class client {
    private Integer client_id;
    private String username;
    private String password_client;
    private String first_name;
    private String last_name;
    private String address_client;
    private String zip_code;
    private String city;
    private String country;
    private String phone;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public client(Integer client_id,
                  String username,
                  String password_client,
                  String first_name,
                  String last_name,
                  String address_client,
                  String zip_code,
                  String city,
                  String country,
                  String phone,
                  String email,
                  LocalDateTime created_at,
                  LocalDateTime modified_at)
    {
        this.client_id = client_id;
        this.username = username;
        this.password_client = password_client;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address_client = address_client;
        this.zip_code = zip_code;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_client() {
        return password_client;
    }

    public void setPassword_client(String password_client) {
        this.password_client = password_client;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress_client() {
        return address_client;
    }

    public void setAddress_client(String address_client) {
        this.address_client = address_client;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
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

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getModified_at() {
        return modified_at;
    }

    public void setModified_at(LocalDateTime modified_at) {
        this.modified_at = modified_at;
    }

    @Override
    public String toString() {
        return "client{" +
                "client_id=" + client_id +
                ", username='" + username + '\'' +
                ", password_client='" + password_client + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address_client='" + address_client + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", created_at=" + created_at +
                ", modified_at=" + modified_at +
                '}';
    }
}
