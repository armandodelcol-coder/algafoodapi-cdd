package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.State;

// Carga intrínsica = 1; Limite = 7
public class StateResponse {

    private Long id;
    private String name;

    // Carga: +1 (State)
    public StateResponse(State state) {
        this.id = state.getId();
        this.name = state.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
