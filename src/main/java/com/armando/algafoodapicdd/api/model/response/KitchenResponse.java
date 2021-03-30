package com.armando.algafoodapicdd.api.model.response;

import com.armando.algafoodapicdd.domain.model.Kitchen;

public class KitchenResponse {

    private Long id;
    private String name;

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
