package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Product;

import java.math.BigDecimal;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
    private RestaurantSummaryResponse restaurant;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.active = product.getActive();
        this.restaurant = new RestaurantSummaryResponse(product.getRestaurant());
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

    public RestaurantSummaryResponse getRestaurant() {
        return restaurant;
    }
}
