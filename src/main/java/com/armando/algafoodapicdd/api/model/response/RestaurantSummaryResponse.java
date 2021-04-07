package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Restaurant;

// Carga intrínsica = 1; Limite = 7
public class RestaurantSummaryResponse {

    private Long id;
    private String name;

    // Carga: +1 (Restaurant)
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
