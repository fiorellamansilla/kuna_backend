package com.kuna_backend.entities;

import java.time.LocalDateTime;

public class Order {
    private Integer order_id;
    private Float order_amount;
    private String ship_name;
    private String ship_address;
    private String order_city;
    private String order_zip;
    private String order_country;
    private String order_phone;
    private String order_email;
    private LocalDateTime ordered_at;
    private LocalDateTime shipped_at;
    private String tracking_number;

    public Order(Integer order_id,
                 Float order_amount,
                 String ship_name,
                 String ship_address,
                 String order_city,
                 String order_zip,
                 String order_country,
                 String order_phone,
                 String order_email,
                 LocalDateTime ordered_at,
                 LocalDateTime shipped_at,
                 String tracking_number)
    {
        this.order_id = order_id;
        this.order_amount = order_amount;
        this.ship_name = ship_name;
        this.ship_address = ship_address;
        this.order_city = order_city;
        this.order_zip = order_zip;
        this.order_country = order_country;
        this.order_phone = order_phone;
        this.order_email = order_email;
        this.ordered_at = ordered_at;
        this.shipped_at = shipped_at;
        this.tracking_number = tracking_number;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Float getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(Float order_amount) {
        this.order_amount = order_amount;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public String getShip_address() {
        return ship_address;
    }

    public void setShip_address(String ship_address) {
        this.ship_address = ship_address;
    }

    public String getOrder_city() {
        return order_city;
    }

    public void setOrder_city(String order_city) {
        this.order_city = order_city;
    }

    public String getOrder_zip() {
        return order_zip;
    }

    public void setOrder_zip(String order_zip) {
        this.order_zip = order_zip;
    }

    public String getOrder_country() {
        return order_country;
    }

    public void setOrder_country(String order_country) {
        this.order_country = order_country;
    }

    public String getOrder_phone() {
        return order_phone;
    }

    public void setOrder_phone(String order_phone) {
        this.order_phone = order_phone;
    }

    public String getOrder_email() {
        return order_email;
    }

    public void setOrder_email(String order_email) {
        this.order_email = order_email;
    }

    public LocalDateTime getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(LocalDateTime ordered_at) {
        this.ordered_at = ordered_at;
    }

    public LocalDateTime getShipped_at() {
        return shipped_at;
    }

    public void setShipped_at(LocalDateTime shipped_at) {
        this.shipped_at = shipped_at;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", order_amount=" + order_amount +
                ", ship_name='" + ship_name + '\'' +
                ", ship_address='" + ship_address + '\'' +
                ", order_city='" + order_city + '\'' +
                ", order_zip='" + order_zip + '\'' +
                ", order_country='" + order_country + '\'' +
                ", order_phone='" + order_phone + '\'' +
                ", order_email='" + order_email + '\'' +
                ", ordered_at=" + ordered_at +
                ", shipped_at=" + shipped_at +
                ", tracking_number='" + tracking_number + '\'' +
                '}';
    }
}
