package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.core.validator.UniqueValue;
import com.armando.algafoodapicdd.domain.model.State;

import javax.validation.constraints.NotBlank;

// Carga intrínsica = 1; Limite = 9
public class StateRequest {

    @NotBlank
    // Carga: +1 (State)
    @UniqueValue(domainClass = State.class, fieldName = "name")
    private String name;

    public String getName() {
        return name;
    }

}
