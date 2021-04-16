package com.armando.algafoodapicdd.domain.model;

import com.armando.algafoodapicdd.api.model.request.ProductRequest;

import javax.persistence.*;
import java.math.BigDecimal;

// Carga intrínsica = 2; Limite = 9
@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 60)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    // Carga: +1 (Restaurant)
    private Restaurant restaurant;

    @Deprecated
    public Product() {
    }

    public Product(String name, String description, BigDecimal price, Boolean active, Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getActive() {
        return active;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    // +1
    public void setPropertiesToUpdate(ProductRequest productRequest) {
        name = productRequest.getName();
        description = productRequest.getDescription();
        price = productRequest.getPrice();
        active = productRequest.getActive();
    }

}
