package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Restaurant;

public class RestaurantSummaryResponse {

    private Long id;
    private String name;

    public RestaurantSummaryResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
