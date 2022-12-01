package com.kuna_backend.entities;

import com.kuna_backend.entities.enums.Color;
import com.kuna_backend.entities.enums.Size;

import java.time.LocalDateTime;

public class item {
    private Integer item_id;
    private String name_item;
    private String desc_item;
    private Size size;
    private Color color;
    private Float price;
    private Float discount;
    private String SKU;
    private Integer quantity_stock;
    private String image_path;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private LocalDateTime deleted_at;

    public item(Integer item_id,
                String name_item,
                String desc_item,
                Size size,
                Color color,
                Float price,
                Float discount,
                String SKU,
                Integer quantity_stock,
                String image_path,
                LocalDateTime created_at,
                LocalDateTime modified_at,
                LocalDateTime deleted_at)
    {
        this.item_id = item_id;
        this.name_item = name_item;
        this.desc_item = desc_item;
        this.size = size;
        this.color = color;
        this.price = price;
        this.discount = discount;
        this.SKU = SKU;
        this.quantity_stock = quantity_stock;
        this.image_path = image_path;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.deleted_at = deleted_at;
    }

    @Override
    public String toString() {
        return "item{" +
                "item_id=" + item_id +
                ", name_item='" + name_item + '\'' +
                ", desc_item='" + desc_item + '\'' +
                ", size=" + size +
                ", color=" + color +
                ", price=" + price +
                ", discount=" + discount +
                ", SKU='" + SKU + '\'' +
                ", quantity_stock=" + quantity_stock +
                ", image_path='" + image_path + '\'' +
                ", created_at=" + created_at +
                ", modified_at=" + modified_at +
                ", deleted_at=" + deleted_at +
                '}';
    }
}

