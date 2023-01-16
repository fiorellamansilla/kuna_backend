package com.kuna_backend.entities;

import com.kuna_backend.entities.enums.Color;
import com.kuna_backend.entities.enums.Size;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class Item {
    private Integer id;
    @Column (name = "name_item")
    private String name;
    @Column (name = "desc_item")
    private String desc;
    private Size size;
    private Color color;
    private Float price;
    private Float discount;
    private String SKU;
    @Column (name = "quantity_stock")
    private Integer quantityStock;
    @Column (name = "image_path")
    private String imagePath;
    @Column (name = "created_at")
    private LocalDateTime createdAt;
    @Column (name = "modified_at")
    private LocalDateTime modifiedAt;
    @Column (name = "deleted_at")
    private LocalDateTime deletedAt;

    public Item(Integer id,
                String name,
                String desc,
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
        this.name = name;
        this.desc = desc;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Item.this.name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = Item.this.desc;
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
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
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

