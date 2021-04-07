package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.City;

// Carga intrínsica = 2; Limite = 7
public class CityResponse {

    private Long id;
    private String name;
    // Carga: +1 (StateResponse)
    private StateResponse state;

    // Carga: +1 (City)
    public CityResponse(City city) {
        this.id = city.getId();
        this.name = city.getName();
        this.state = new StateResponse(city.getState());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StateResponse getState() {
        return state;
    }

}
