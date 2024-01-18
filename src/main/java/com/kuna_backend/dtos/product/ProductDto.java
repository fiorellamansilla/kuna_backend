package com.kuna_backend.dtos.product;

import com.kuna_backend.models.Product;
import org.jetbrains.annotations.NotNull;

public class ProductDto {

    private long id;
    private @NotNull String name;
    private @NotNull Double price;
    private @NotNull String description;
    private @NotNull String imageUrl;
    private @NotNull Integer categoryId;

    public ProductDto(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setPrice(product.getPrice());
        this.setDescription(product.getDescription());
        this.setImageUrl(product.getImageUrl());
        this.setCategoryId(product.getCategory().getId());
    }

    public ProductDto(@NotNull String name, @NotNull Double price, @NotNull String description, @NotNull String imageUrl, @NotNull Integer categoryId) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
