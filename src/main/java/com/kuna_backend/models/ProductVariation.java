package com.kuna_backend.models;

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
    private Integer id;

    @Column (name = "size", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    Size size;

    @Column (name = "color", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    Color color;

    @Column(name = "quantity_stock", nullable = false)
    Integer quantityStock;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public ProductVariation() {
    }

    public ProductVariation(Integer id, Size size, Color color, Integer quantityStock, Product product) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.quantityStock = quantityStock;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(Integer quantityStock) {
        this.quantityStock = quantityStock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
