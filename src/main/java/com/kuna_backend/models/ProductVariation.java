package com.kuna_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuna_backend.dtos.product.ProductVariationDto;
import com.kuna_backend.enums.Color;
import com.kuna_backend.enums.Size;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_variations")
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "size", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    private Size size;

    @Column (name = "color", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "quantity_stock", nullable = false)
    private int quantityStock;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public ProductVariation (ProductVariationDto productVariationDto, Product product) {
        this.size = productVariationDto.getSize();
        this.color = productVariationDto.getColor();
        this.quantityStock = productVariationDto.getQuantityStock();
        this.product = product;
    }
    public ProductVariation(long id, Size size, Color color, int quantityStock, Product product) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.quantityStock = quantityStock;
        this.product = product;
    }

    public ProductVariation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
