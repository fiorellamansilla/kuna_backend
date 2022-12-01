package com.kuna_backend.entities;

import java.time.LocalDateTime;

public class order {
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

    public order(Integer order_id,
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

    @Override
    public String toString() {
        return "order{" +
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
