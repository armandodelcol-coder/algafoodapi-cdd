package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.validator.UniqueValue;
import com.armando.algafoodapicdd.domain.model.State;

import javax.validation.constraints.NotBlank;

public class StateRequest {

    @NotBlank
    @UniqueValue(domainClass = State.class, fieldName = "name")
    private String name;

    public String getName() {
        return name;
    }

}
