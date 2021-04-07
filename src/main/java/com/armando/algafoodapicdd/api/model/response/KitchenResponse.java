package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Kitchen;

// Carga intrínsica = 1; Limite = 7
public class KitchenResponse {

    private Long id;
    private String name;

    // Carga: +1 (Kitchen)
    public KitchenResponse(Kitchen kitchen) {
        this.id = kitchen.getId();
        this.name = kitchen.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
