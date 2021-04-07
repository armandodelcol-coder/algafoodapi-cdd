package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.validator.UniqueValue;
import com.armando.algafoodapicdd.domain.model.Kitchen;

import javax.validation.constraints.NotBlank;

// Carga intrínsica = 1; Limite = 9
public class KitchenRequest {

    // Carga: +1 (Kitchen)
    @UniqueValue(domainClass = Kitchen.class, fieldName = "name")
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
