package com.kuna_backend.dtos.product;

import com.kuna_backend.models.Product;
import org.jetbrains.annotations.NotNull;

public class ProductDto {

    private long id;
    private @NotNull String name;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull String imageUrl;
    private long categoryId;

    public ProductDto(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setPrice(product.getPrice());
        this.setDescription(product.getDescription());
        this.setImageUrl(product.getImageUrl());
        this.setCategoryId(product.getCategory().getId());
    }

    public ProductDto(@NotNull String name, @NotNull double price, @NotNull String description, @NotNull String imageUrl, @NotNull long categoryId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public ProductDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
