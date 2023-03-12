package com.kuna_backend.dtos.item;

import com.kuna_backend.enums.Category;
import com.kuna_backend.models.Item;

public class ItemDto {

    private Integer id;
    private String name;
    private String imagePath;
    private Float price;
    private String description;
    private Category category;

    public ItemDto(Item item) {
        this.setId(item.getId());
        this.setName(item.getName());
        this.setImagePath(item.getImagePath());
        this.setDescription(item.getDescription());
        this.setPrice(item.getPrice());
        this.setCategory(item.getCategory());
    }

    public ItemDto(Integer id, String name, String imagePath, Float price, String description, Category category) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public ItemDto() {
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
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
