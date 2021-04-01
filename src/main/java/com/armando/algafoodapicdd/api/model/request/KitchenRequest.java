package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.validator.UniqueValue;
import com.armando.algafoodapicdd.domain.model.Kitchen;

import javax.validation.constraints.NotBlank;

public class KitchenRequest {

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
