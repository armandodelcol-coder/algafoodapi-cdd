package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Restaurant;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class RestaurantResponse {

    private Long id;
    private String name;
    private BigDecimal deliveryTax;
    private KitchenResponse kitchen;
    private Boolean active;
    private Boolean open;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private AddressResponse address;

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.deliveryTax = restaurant.getDeliveryTax();
        this.kitchen = new KitchenResponse(restaurant.getKitchen());
        this.active = restaurant.getActive();
        this.open = restaurant.getOpen();
        this.createdAt = restaurant.getCreatedAt();
        this.updatedAt = restaurant.getUpdatedAt();
        this.address = new AddressResponse(restaurant.getAddress());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDeliveryTax() {
        return deliveryTax;
    }

    public KitchenResponse getKitchen() {
        return kitchen;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getOpen() {
        return open;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public AddressResponse getAddress() {
        return address;
    }
}
