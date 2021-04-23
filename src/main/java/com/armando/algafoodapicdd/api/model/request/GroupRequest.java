package com.armando.algafoodapicdd.api.model.request;

import com.armando.algafoodapicdd.api.core.validator.UniqueValue;
import com.armando.algafoodapicdd.domain.model.Group;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Carga intrínsica = 1; Limite = 9
public class GroupRequest {

    // Carga: +1 (Group)
    @UniqueValue(domainClass = Group.class, fieldName = "name")
    @NotBlank
    @Size(max = 60)
    private String name;

    public String getName() {
        return name;
    }

}
