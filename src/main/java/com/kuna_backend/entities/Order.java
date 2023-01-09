package com.kuna_backend.entities;

import java.time.LocalDateTime;

public class Order {
    private Integer id;
    private Float orderAmount;
    private String shipName;
    private String shipAddress;
    private String orderCity;
    private String orderZip;
    private String orderCountry;
    private String orderPhone;
    private String orderEmail;
    private LocalDateTime orderedAt;
    private LocalDateTime shippedAt;
    private String trackingNumber;

    public Order(Integer id,
                 Float orderAmount,
                 String shipName,
                 String shipAddress,
                 String orderCity,
                 String orderZip,
                 String orderCountry,
                 String orderPhone,
                 String orderEmail,
                 LocalDateTime orderedAt,
                 LocalDateTime shippedAt,
                 String trackingNumber)
    {
        this.id = Order.this.id;
        this.orderAmount = orderAmount;
        this.shipName = shipName;
        this.shipAddress = shipAddress;
        this.orderCity = orderCity;
        this.orderZip = orderZip;
        this.orderCountry = orderCountry;
        this.orderPhone = orderPhone;
        this.orderEmail = orderEmail;
        this.orderedAt = orderedAt;
        this.shippedAt = shippedAt;
        this.trackingNumber = trackingNumber;
    }

    public Integer getOrderId() {
        return id;
    }

    public void setOrderId(Integer id) {
        this.id = Order.this.id;
    }

    public Float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public String getOrderZip() {
        return orderZip;
    }

    public void setOrderZip(String orderZip) {
        this.orderZip = orderZip;
    }

    public String getOrderCountry() {
        return orderCountry;
    }

    public void setOrderCountry(String orderCountry) {
        this.orderCountry = orderCountry;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getOrderEmail() {
        return orderEmail;
    }

    public void setOrderEmail(String orderEmail) {
        this.orderEmail = orderEmail;
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
                ", orderAmount=" + orderAmount +
                ", shipName='" + shipName + '\'' +
                ", shipAddress='" + shipAddress + '\'' +
                ", orderCity='" + orderCity + '\'' +
                ", orderZip='" + orderZip + '\'' +
                ", orderCountry='" + orderCountry + '\'' +
                ", orderPhone='" + orderPhone + '\'' +
                ", orderEmail='" + orderEmail + '\'' +
                ", orderedAt=" + orderedAt +
                ", shippedAt=" + shippedAt +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }
}
