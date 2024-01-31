package com.kuna_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuna_backend.dtos.product.ProductDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "name_product", length = 128, nullable = false)
    private String name;
    @Column (name = "desc_product", length = 2048, nullable = false)
    private String description;
    @Column (name = "price", nullable = false)
    private double price;
    @Column (name = "image_url", length = 256, nullable = false)
    private String imageUrl;
    @Column (name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column (name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @OneToMany (mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductVariation> productVariations;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    Category category;

    public Product(ProductDto productDto, Category category) {
        this.name = productDto.getName();
        this.description = productDto.getDescription();
        this.price = productDto.getPrice();
        this.imageUrl = productDto.getImageUrl();
        this.category = category;
        productVariations = new ArrayList<>();
    }

    public Product(Long id, String name, String description, double price, String imageUrl, LocalDateTime createdAt, LocalDateTime modifiedAt, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.category = category;
        productVariations = new ArrayList<>();
    }

    public Product() {
        productVariations = new ArrayList<>();
    }

    public Product(Long productId, ProductDto productDto, Category category) {
        productVariations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public List<ProductVariation> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(List<ProductVariation> productVariations) {
        this.productVariations = productVariations;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}


