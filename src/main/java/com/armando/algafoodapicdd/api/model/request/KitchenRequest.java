package com.armando.algafoodapicdd.api.model.request;

import javax.validation.constraints.NotBlank;

public class KitchenRequest {

    @NotBlank
    private String name;

    @Deprecated
    public KitchenRequest() {
    }

    public KitchenRequest(@NotBlank String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
