package com.kuna_backend.entities;

import com.kuna_backend.entities.enums.Color;
import com.kuna_backend.entities.enums.Size;

import java.time.LocalDateTime;

public class Item {
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

    public Item(Integer item_id,
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

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public String getName_item() {
        return name_item;
    }

    public void setName_item(String name_item) {
        this.name_item = name_item;
    }

    public String getDesc_item() {
        return desc_item;
    }

    public void setDesc_item(String desc_item) {
        this.desc_item = desc_item;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public Integer getQuantity_stock() {
        return quantity_stock;
    }

    public void setQuantity_stock(Integer quantity_stock) {
        this.quantity_stock = quantity_stock;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
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

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    @Override
    public String toString() {
        return "Item{" +
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

