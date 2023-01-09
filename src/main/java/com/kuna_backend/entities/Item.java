package com.kuna_backend.entities;

import com.kuna_backend.entities.enums.Color;
import com.kuna_backend.entities.enums.Size;

import java.time.LocalDateTime;

public class Item {
    private Integer id;
    private String nameItem;
    private String descItem;
    private Size size;
    private Color color;
    private Float price;
    private Float discount;
    private String SKU;
    private Integer quantityStock;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public Item(Integer id,
                String nameItem,
                String descItem,
                Size size,
                Color color,
                Float price,
                Float discount,
                String SKU,
                Integer quantityStock,
                String imagePath,
                LocalDateTime createdAt,
                LocalDateTime modifiedAt,
                LocalDateTime deletedAt)
    {
        this.id = id;
        this.nameItem = nameItem;
        this.descItem = descItem;
        this.size = size;
        this.color = color;
        this.price = price;
        this.discount = discount;
        this.SKU = SKU;
        this.quantityStock = quantityStock;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;
    }

    public Integer getItemId() {
        return id;
    }

    public void setItemId(Integer id) {
        this.id = id;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getDescItem() {
        return descItem;
    }

    public void setDescItem(String descItem) {
        this.descItem = descItem;
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

    public Integer getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(Integer quantityStock) {
        this.quantityStock = quantityStock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nameItem='" + nameItem + '\'' +
                ", descItem='" + descItem + '\'' +
                ", size=" + size +
                ", color=" + color +
                ", price=" + price +
                ", discount=" + discount +
                ", SKU='" + SKU + '\'' +
                ", quantityStock=" + quantityStock +
                ", imagePath='" + imagePath + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

