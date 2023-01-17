package com.kuna_backend.entities;

import com.kuna_backend.entities.enums.Color;
import com.kuna_backend.entities.enums.Size;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table (name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column (name = "name_item", length = 128, nullable = false)
    private String name;
    @Column (name = "desc_item", length = 2048, nullable = false)
    private String desc;
    @Column (name = "size", length = 64, nullable = false)
    @Enumerated (EnumType.STRING)
    private Size size;
    @Column (name = "color", length = 64, nullable = false)
    @Enumerated (EnumType.STRING)
    private Color color;
    @Column (name = "price", nullable = false)
    private Float price = 0.0f;
    @Column (name = "discount", nullable = false)
    private Float discount = 0.0f;
    @Column (name = "SKU", length = 128, nullable = false)
    private String SKU;
    @Column (name = "quantity_stock", nullable = false)
    private Integer quantityStock = 0;
    @Column (name = "image_path", length = 256, nullable = false)
    private String imagePath;
    @Column (name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column (name = "modified_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @Column (name = "deleted_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime deletedAt;

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

